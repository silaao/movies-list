package com.silasdev.movielist

import MovieViewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
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
    var expanded by remember { mutableStateOf(false) } // Estado para controlar a expansão
    var isOverviewTruncated by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) { // Usar Column para organizar imagem e texto
            Row {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w200${movie.posterPath}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(movie.title, style = MaterialTheme.typography.titleMedium)
                    Text("Lançamento: ${movie.releaseDate}")
                    Text("⭐ ${movie.voteAverage}")
                    // 1. Text do Overview
                    Text(
                        text = movie.overview,
                        maxLines = if (expanded) Int.MAX_VALUE else 3,
                        // Controla o número de linhas
                        overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis, // Controla o "..."
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        },
                        // Torna o texto clicável
                        // *** Aqui está a chave: onTextLayout ***
                        onTextLayout = { textLayoutResult ->
                            // Só precisamos verificar se está truncado quando não está expandido.
                            if (!expanded) {
                                isOverviewTruncated =
                                    textLayoutResult.isLineEllipsized(textLayoutResult.lineCount - 1)
                            } else {
                                // Quando expandido, o botão "Menos" sempre aparece se o texto foi truncado ANTES
                                // Isso previne um "flicker" de estado.
                            }
                        }
                    )
                    // Botão "Mais" ou "Menos
                    // 2. Botão "Mais" ou "Menos"
                    // O botão "Mais" é exibido SE o texto estiver truncado.
                    // O botão "Menos" é exibido SE o estado for expandido.
                    if (isOverviewTruncated || expanded) {
                        Text(
                            text = if (expanded) "Menos" else "Mais",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable { expanded = !expanded }
                                .padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp)) // Espaçamento entre as informações e o overview


                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(), // Ocupa a largura total para que a centralização funcione
                horizontalAlignment = Alignment.CenterHorizontally // Centraliza o conteúdo horizontalmente
            ) {
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
}