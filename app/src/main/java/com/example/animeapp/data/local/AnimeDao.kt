package com.example.animeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY score DESC")
    fun observeAnimeList(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE anime_id = :animeId")
    suspend fun getAnimeById(animeId: Int): AnimeEntity?

    @Query("DELETE FROM anime")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AnimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AnimeEntity)
}