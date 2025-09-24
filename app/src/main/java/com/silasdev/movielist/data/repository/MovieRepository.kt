package com.silasdev.movielist.data.repository

import com.silasdev.movielist.data.local.Movie
import com.silasdev.movielist.data.local.MovieDao

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies = movieDao.getAllMovies()

    suspend fun insert(movie: Movie) = movieDao.insert(movie)
    suspend fun update(movie: Movie) = movieDao.update(movie)
    suspend fun delete(movie: Movie) = movieDao.delete(movie)
}