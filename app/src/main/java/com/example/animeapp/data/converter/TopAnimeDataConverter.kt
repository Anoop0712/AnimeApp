package com.example.animeapp.data.converter

import com.example.animeapp.data.remote.model.TopAnimeApiResponse
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ErrorType
import com.example.animeapp.domain.model.ResponseState
import javax.inject.Inject

class TopAnimeDataConverter @Inject constructor() {

    fun apply(response: TopAnimeApiResponse): ResponseState<List<Anime>> {
        return if (response.data.any { data ->
                data.malId.toString().isNotBlank() && (data.titles?.any { it.title.orEmpty().isNotBlank() } ?: false)
            }) {
            ResponseState.Success(
                response.data.filter { data ->
                    data.malId.toString().isNotBlank() && (data.titles?.any { it.title.orEmpty().isNotBlank() } ?: false)
                }.map { data ->
                    Anime(
                        id = data.malId,
                        title = data.titles?.firstOrNull()?.title.orEmpty(),
                        synopsis = data.synopsis.orEmpty(),
                        episodes = data.episodes ?: 0,
                        rating = data.score ?: 0.0,
                        posterUrl = data.images?.jpgFormat?.imageUrl.orEmpty(),
                        trailerUrl = data.trailer?.url ?: data.trailer?.embedUrl.orEmpty(),
                        rank = data.rank ?: 0,
                        score = data.score ?: 0.0
                    )
                }
            )
        } else {
            ResponseState.Error(ErrorType.NO_DATA)
        }
    }
}