package com.example.animeapp.domain.repository

import com.example.animeapp.data.local.AnimeEntity
import kotlinx.coroutines.flow.Flow

interface AnimeRoomDbDataSource {
    suspend fun insert(entity: AnimeEntity)

    suspend fun insertAll(list: List<AnimeEntity>)

    fun observeAnimeList(): Flow<List<AnimeEntity>>

    suspend fun getAnimeById(animeId: Int): AnimeEntity?

    suspend fun clearAll()
}