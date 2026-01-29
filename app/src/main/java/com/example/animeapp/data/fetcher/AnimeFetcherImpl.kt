package com.example.animeapp.data.fetcher

import com.example.animeapp.data.remote.backend.AnimeServiceBackend
import com.example.animeapp.data.remote.model.AnimeDetailResponse
import com.example.animeapp.data.remote.model.TopAnimeApiResponse
import javax.inject.Inject

class AnimeFetcherImpl @Inject constructor(
    private val animeServiceBackend: AnimeServiceBackend
): AnimeFetcher {
    override suspend fun getTopAnimeData(url: String): TopAnimeApiResponse {
        return animeServiceBackend.getTopAnimeData(url)
    }

    override suspend fun getAnimeDetailById(url: String): AnimeDetailResponse {
        return animeServiceBackend.getAnimeDetailById(url)
    }
}