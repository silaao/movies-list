package com.silasdev.movielist

import MovieViewModel
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.silasdev.movielist.data.remote.TmdbMovie
import com.silasdev.movielist.ui.NowPlayingViewModel

@Composable
fun NowPlayingScreen(
    apiKey: String,
    movieViewModel: MovieViewModel,
    viewModel: NowPlayingViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchNowPlaying(apiKey)
    }

    val movies by viewModel.movies.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieCard(movie) {
                // Ao clicar no botão, adiciona no Room
                movieViewModel.addMovie(title = movie.title, watched = false)
            }
        }
    }
}

@Composable
fun MovieCard(movie: TmdbMovie, onAddClick: () -> Unit) {
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
        Column(
            modifier = Modifier.fillMaxWidth(), // Ocupa a largura total para que a centralização funcione
            horizontalAlignment = Alignment.CenterHorizontally // Centraliza o conteúdo horizontalmente, eu quiser
        ) {
            val context = LocalContext.current
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                    onAddClick()
                    Toast.makeText(
                        context,
                        "Filme adicionado à sua lista!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar à Minha Lista")
            }

        }
    }
}
