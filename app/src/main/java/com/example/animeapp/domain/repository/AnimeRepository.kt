package com.example.animeapp.domain.repository

import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getTopAnime(): Flow<ResponseState<List<Anime>>>

    fun getAnimeDetail(id: Int): Flow<ResponseState<Anime>>
}