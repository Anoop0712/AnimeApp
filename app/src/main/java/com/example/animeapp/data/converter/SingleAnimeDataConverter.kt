package com.example.animeapp.data.converter

import com.example.animeapp.data.remote.model.AnimeDetailResponse
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ErrorType
import com.example.animeapp.domain.model.ResponseState
import javax.inject.Inject

class SingleAnimeDataConverter @Inject constructor() {
    fun apply(response: AnimeDetailResponse): ResponseState<Anime> {
        return if (response.data.malId.toString().isNotBlank() && (response.data.titles?.any { it.title.orEmpty().isNotBlank() } ?: false)) {
            ResponseState.Success(
                Anime(
                    id = response.data.malId,
                    title = response.data.titles.firstOrNull()?.title.orEmpty(),
                    synopsis = response.data.synopsis.orEmpty(),
                    episodes = response.data.episodes ?: 0,
                    rating = response.data.score ?: 0.0,
                    posterUrl = response.data.images?.jpgFormat?.imageUrl.orEmpty(),
                    trailerUrl = response.data.trailer?.url ?: response.data.trailer?.embedUrl.orEmpty(),
                    rank = response.data.rank ?: 0,
                    score = response.data.score ?: 0.0,
                    genres = response.data.genres?.joinToString("_") { it.name }.orEmpty()
                )
            )
        } else {
            ResponseState.Error(ErrorType.NO_DATA)
        }
    }
}