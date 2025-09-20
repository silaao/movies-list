package com.silasdev.movielist

import MovieScreen
import MovieViewModel
import android.R.attr.apiKey
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MovieApp() {
    val tabs = listOf("Minha Lista", "Filmes em Cartaz")
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val movieViewModel: MovieViewModel = viewModel() // seu viewmodel do Room

            when (selectedTab) {
                0 -> MovieScreen()
                1 -> NowPlayingScreen(apiKey = "5ac3694702a27824c2bb5c54a0506de4",
                    movieViewModel = movieViewModel)
            }
        }
    }
}