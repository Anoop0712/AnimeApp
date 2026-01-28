package com.example.animeapp.di

import com.example.animeapp.data.remote.backend.AnimeServiceBackend
import dagger.Module
import dagger.Provides
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
}