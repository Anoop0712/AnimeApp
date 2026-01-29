package com.example.animeapp.data.local

import com.example.animeapp.domain.repository.AnimeRoomDbDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeRoomDbDataSourceImpl @Inject constructor(
    private val animeDao: AnimeDao
) : AnimeRoomDbDataSource {
    override suspend fun insert(entity: AnimeEntity) {
        animeDao.insert(entity)
    }

    override suspend fun insertAll(list: List<AnimeEntity>) {
        animeDao.insertAll(list)
    }

    override fun observeAnimeList(): Flow<List<AnimeEntity>> {
        return animeDao.observeAnimeList()
    }

    override suspend fun getAnimeById(animeId: Int): AnimeEntity? {
        return animeDao.getAnimeById(animeId)
    }

    override suspend fun clearAll() {
        return animeDao.clearAll()
    }
}