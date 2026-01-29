package com.example.animeapp.data.converter

import com.example.animeapp.data.local.AnimeEntity
import com.example.animeapp.domain.model.Anime
import javax.inject.Inject

class AnimeToAnimeEntityConverter @Inject constructor() {

    fun fromApi(anime: Anime): AnimeEntity {
        return AnimeEntity(
            animeId = anime.id,
            title = anime.title,
            episodes = anime.episodes,
            score = anime.score,
            posterUrl = anime.posterUrl,
            synopsis = anime.synopsis,
            rank = anime.rank,
            rating = anime.rating,
            trailerUrl = anime.trailerUrl
        )
    }

    fun fromApiList(animeList: List<Anime>): List<AnimeEntity> =
        animeList.map { fromApi(it) }
}
