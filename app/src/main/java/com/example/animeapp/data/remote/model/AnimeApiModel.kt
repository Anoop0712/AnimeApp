package com.example.animeapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopAnimeApiResponse(
    val data: List<AnimeData>
)

data class AnimeDetailResponse(
    val data: AnimeData
)

data class AnimeData(
    @SerializedName("mal_id") val malId: Int,
    val url: String?,
    val images: AnimeImages?,
    val trailer: AnimeTrailer?,
    val approved: Boolean?,
    val titles: List<Title>?,
    val type: String?,
    val source: String?,
    val episodes: Int?,
    val status: String?,
    val airing: Boolean?,
    val duration: String?,
    val rating: String?,
    val score: Double?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val broadcast: Broadcast?,
    val producers: List<CommonObject>?,
    val licensors: List<CommonObject>?,
    val studios: List<CommonObject>?,
    val genres: List<Genre>?,
    @SerializedName("scored_by") val scoredBy: Int?,
    @SerializedName("explicit_genres") val explicitGenres: List<Genre>?
)

data class AnimeImages(
    @SerializedName("jpg") val jpgFormat: ImageUrlData?
)

data class ImageUrlData(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("small_image_url") val smallImageUrl: String,
    @SerializedName("large_image_url") val largeImageUrl: String?
)

data class AnimeTrailer(
    val url: String?,
    @SerializedName("youtube_id") val youtubeId: String?,
    @SerializedName("embed_url") val embedUrl: String?
)

data class Genre(
    @SerializedName("mal_id") val malId: Int?,
    val type: String?,
    val name: String,
    val url: String?
)

data class Broadcast(
    val day: String?,
    val time: String?,
    val timezone: String?,
    @SerializedName("string") val string: String?
)

data class CommonObject(
    @SerializedName("mal_id") val malId: Int?,
    val type: String?,
    val name: String,
    val url: String?
)

data class Title(
    val type: String?,
    val title: String?
)