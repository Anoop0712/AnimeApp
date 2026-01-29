package com.example.animeapp.data.repository

import com.example.animeapp.data.converter.SingleAnimeDataConverter
import com.example.animeapp.data.converter.TopAnimeDataConverter
import com.example.animeapp.data.fetcher.AnimeFetcher
import com.example.animeapp.di.IoDispatcher
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ResponseState
import com.example.animeapp.domain.repository.AnimeRepository
import com.example.animeapp.utils.ErrorHandler
import com.example.animeapp.utils.onError
import com.example.animeapp.utils.startFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeFetcher: AnimeFetcher,
    private val topAnimeDataConverter: TopAnimeDataConverter,
    private val singleAnimeDataConverter: SingleAnimeDataConverter,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): AnimeRepository {
    override fun getTopAnime(): Flow<ResponseState<List<Anime>>> {
        return flow { emit(animeFetcher.getTopAnimeData("https://api.jikan.moe/v4/top/anime")) }
            .map { topAnimeDataConverter.apply(it) }
            .onError(ErrorHandler())
            .startFlow(ResponseState.Loading)
            .flowOn(ioDispatcher)
    }

    override fun getAnimeDetail(id: Int): Flow<ResponseState<Anime>> {
        return flow { emit(animeFetcher.getAnimeDetailById("https://api.jikan.moe/v4/anime/$id")) }
            .map { singleAnimeDataConverter.apply(it) }
            .onError(ErrorHandler())
            .startFlow(ResponseState.Loading)
            .flowOn(ioDispatcher)
    }
}