package com.silasdev.movielist.data.remote

import com.google.gson.annotations.SerializedName

data class TmdbMovie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double
)

data class TmdbResponse(
    val results: List<TmdbMovie>
)
