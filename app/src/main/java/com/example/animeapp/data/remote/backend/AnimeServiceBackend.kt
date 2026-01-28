package com.example.animeapp.data.remote.backend

import com.example.animeapp.data.remote.model.AnimeDetailResponse
import com.example.animeapp.data.remote.model.TopAnimeApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface AnimeServiceBackend {

    @GET
    suspend fun getTopAnimeData(
        @Url url: String,
    ): TopAnimeApiResponse

    @GET
    suspend fun getAnimeDetailById(
        @Url url: String,
    ): AnimeDetailResponse
}