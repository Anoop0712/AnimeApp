package com.example.animeapp.domain.model

import kotlin.random.Random

data class Anime(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val synopsis: String = "",
    val episodes: Int = 0,
    val rating: Double = 0.0,
    val posterUrl: String = "",
    val trailerUrl: String = "",
    val genres: String = "",
    val rank: Int = 0,
    val score: Double = 0.0
)
