package com.example.animeapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.animeapp.data.converter.AnimeEntityToAnimeConverter
import com.example.animeapp.data.converter.AnimeToAnimeEntityConverter
import com.example.animeapp.data.converter.SingleAnimeDataConverter
import com.example.animeapp.data.converter.TopAnimeDataConverter
import com.example.animeapp.data.fetcher.AnimeFetcher
import com.example.animeapp.di.IoDispatcher
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ErrorType
import com.example.animeapp.domain.model.ResponseState
import com.example.animeapp.domain.repository.AnimeRepository
import com.example.animeapp.domain.repository.AnimeRoomDbDataSource
import com.example.animeapp.utils.ErrorHandler
import com.example.animeapp.utils.onError
import com.example.animeapp.utils.startFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeFetcher: AnimeFetcher,
    private val topAnimeDataConverter: TopAnimeDataConverter,
    private val singleAnimeDataConverter: SingleAnimeDataConverter,
    private val animeDataSource: AnimeRoomDbDataSource,
    private val animeEntityToAnimeConverter: AnimeEntityToAnimeConverter,
    private val animeToAnimeEntityConverter: AnimeToAnimeEntityConverter,
    private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AnimeRepository {


    override fun getTopAnime(): Flow<ResponseState<List<Anime>>> {
        return if (!isNetworkAvailable()) {
            return animeDataSource.observeAnimeList().map { entities ->
                if (entities.isNotEmpty()) {
                    ResponseState.Success(animeEntityToAnimeConverter.fromEntityList(entities))
                } else {
                    ResponseState.Error(ErrorType.NO_INTERNET_CONNECTION)
                }
            }.onError(ErrorHandler())
                .startFlow(ResponseState.Loading)
                .flowOn(ioDispatcher)
        } else {
            flow { emit(animeFetcher.getTopAnimeData("https://api.jikan.moe/v4/top/anime")) }
                .map {
                    val result = topAnimeDataConverter.apply(it)
                    if (result is ResponseState.Success) {
                        animeDataSource.clearAll()
                        animeDataSource.insertAll(animeToAnimeEntityConverter.fromApiList(result.data))
                    }
                    result
                }
                .onError(ErrorHandler())
                .startFlow(ResponseState.Loading)
                .flowOn(ioDispatcher)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAnimeDetail(id: Int): Flow<ResponseState<Anime>> {
        return flow {
            emit(animeDataSource.getAnimeById(id))
        }
            .flatMapLatest { cachedAnime ->
                if (cachedAnime == null ||
                    (isDataExpired(cachedAnime.lastUpdated))
                ) {
                    flow {
                        emit(
                            animeFetcher.getAnimeDetailById(
                                "https://api.jikan.moe/v4/anime/$id"
                            )
                        )
                    }.map { response ->
                        val result = singleAnimeDataConverter.apply(response)
                        if (result is ResponseState.Success) {
                            animeDataSource.insert(animeToAnimeEntityConverter.fromApi(result.data))
                        }
                        result
                    }
                } else {
                    flowOf(
                        ResponseState.Success(
                            animeEntityToAnimeConverter.fromEntity(
                                cachedAnime
                            )
                        )
                    )
                }
            }
            .onError(ErrorHandler())
            .startFlow(ResponseState.Loading)
            .flowOn(ioDispatcher)

    }


    private fun isDataExpired(timestamp: Long): Boolean {
        return System.currentTimeMillis() - timestamp > THRESHOLD_DAYS
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    companion object {
        private val THRESHOLD_DAYS = TimeUnit.DAYS.toMillis(1)
    }
}