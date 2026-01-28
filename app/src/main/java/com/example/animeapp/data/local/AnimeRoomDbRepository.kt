package com.example.animeapp.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeRoomDbRepository @Inject constructor(
    private val animeDao: AnimeDao
) {
    suspend fun insert(entity: AnimeEntity) {
        animeDao.insert(entity)
    }

    suspend fun insertAll(list: List<AnimeEntity>) {
        animeDao.insertAll(list)
    }

    fun observeAnimeList(): Flow<List<AnimeEntity>> {
        return animeDao.observeAnimeList()
    }

    fun observeAnime(id: Int): Flow<AnimeEntity?> {
        return animeDao.observeAnime(id)
    }
}