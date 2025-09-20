package com.silasdev.movielist.network

import com.silasdev.movielist.data.TmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ): TmdbResponse
}
