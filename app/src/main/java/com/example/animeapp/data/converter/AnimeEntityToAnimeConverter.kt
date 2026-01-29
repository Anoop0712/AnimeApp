package com.example.animeapp.data.converter

import com.example.animeapp.data.local.AnimeEntity
import com.example.animeapp.domain.model.Anime
import javax.inject.Inject

class AnimeEntityToAnimeConverter @Inject constructor() {

    fun fromEntity(entity: AnimeEntity): Anime {
        return Anime(
            id = entity.animeId,
            title = entity.title,
            episodes = entity.episodes ?: 0,
            score = entity.score ?: 0.0,
            synopsis = entity.synopsis.orEmpty(),
            rating = entity.rating ?: 0.0,
            posterUrl = entity.posterUrl.orEmpty(),
            trailerUrl = entity.trailerUrl.orEmpty(),
            rank = entity.rank ?: 0
        )
    }

    fun fromEntityList(entities: List<AnimeEntity>): List<Anime> =
        entities.map { fromEntity(it) }
}
