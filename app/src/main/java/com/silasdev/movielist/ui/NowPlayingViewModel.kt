package com.silasdev.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silasdev.movielist.data.TmdbMovie
import com.silasdev.movielist.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NowPlayingViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<TmdbMovie>>(emptyList())
    val movies: StateFlow<List<TmdbMovie>> = _movies

    fun fetchNowPlaying(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getNowPlaying(apiKey)
                _movies.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
