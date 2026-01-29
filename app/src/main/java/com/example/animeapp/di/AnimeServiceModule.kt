package com.example.animeapp.di

import com.example.animeapp.data.converter.SingleAnimeDataConverter
import com.example.animeapp.data.converter.TopAnimeDataConverter
import com.example.animeapp.data.fetcher.AnimeFetcher
import com.example.animeapp.data.fetcher.AnimeFetcherImpl
import com.example.animeapp.data.remote.backend.AnimeServiceBackend
import com.example.animeapp.data.repository.AnimeRepositoryImpl
import com.example.animeapp.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AnimeServiceModule {

    @Provides
    @Singleton
    fun provideBrowseXlpBackend(
        retrofit: Retrofit
    ): AnimeServiceBackend {
        return retrofit.create(AnimeServiceBackend::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeFetcher(
        backend: AnimeServiceBackend
    ): AnimeFetcher {
        return AnimeFetcherImpl(backend)
    }

    @Provides
    @Singleton
    fun provideBrowseXlpRepository(
        animeFetcher: AnimeFetcher,
        topAnimeDataConverter: TopAnimeDataConverter,
        singleAnimeDataConverter: SingleAnimeDataConverter,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            animeFetcher,
            topAnimeDataConverter,
            singleAnimeDataConverter,
            ioDispatcher
        )
    }
}