package com.silasdev.movielist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Aqui você poderia buscar os filmes de uma API, mas para exemplo vou usar lista fixa
val nowPlayingMovies = listOf(
    "Duna: Parte 2",
    "Deadpool & Wolverine",
    "Beetlejuice Beetlejuice",
    "Meu Malvado Favorito 4"
)

@Composable
fun NowPlayingScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(nowPlayingMovies) { movieTitle ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Text(
                    text = movieTitle,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

