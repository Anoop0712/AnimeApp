package com.example.animeapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey
    @ColumnInfo(name = "anime_id")
    val animeId: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val rank: Int?,
    val rating: Double?,
    val cast: List<String>?,
    val posterUrl: String?,
    val trailerUrl: String?,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis()
)