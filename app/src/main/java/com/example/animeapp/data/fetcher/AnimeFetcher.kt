package com.example.animeapp.data.fetcher

import com.example.animeapp.data.remote.model.AnimeDetailResponse
import com.example.animeapp.data.remote.model.TopAnimeApiResponse

interface AnimeFetcher {
    suspend fun getTopAnimeData(
        url: String
    ): TopAnimeApiResponse

    suspend fun getAnimeDetailById(
       url: String,
    ): AnimeDetailResponse
}