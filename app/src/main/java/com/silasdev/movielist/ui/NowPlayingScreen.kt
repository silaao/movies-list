package com.silasdev.movielist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.silasdev.movielist.data.TmdbMovie
import com.silasdev.movielist.ui.NowPlayingViewModel

// Aqui você poderia buscar os filmes de uma API, mas para exemplo vou usar lista fixa
val nowPlayingMovies = listOf(
    "Duna: Parte 2",
    "Deadpool & Wolverine",
    "Beetlejuice Beetlejuice",
    "Meu Malvado Favorito 4"
)

@Composable
fun NowPlayingScreen(
    apiKey: String,
    viewModel: NowPlayingViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchNowPlaying(apiKey)
    }

    val movies by viewModel.movies.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieCard(movie)
        }
    }
}

@Composable
fun MovieCard(movie: TmdbMovie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w200${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                Text("Lançamento: ${movie.releaseDate}")
                Text("⭐ ${movie.voteAverage}")
                Text(movie.overview, maxLines = 3, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
