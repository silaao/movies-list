import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silasdev.movielist.NowPlayingScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MovieApp() {
    val tabs = listOf("Minha Lista", "Filmes em Cartaz")
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope() // Cria um CoroutineScope para as animações

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier.padding(innerPadding)
        ) { page ->
            val movieViewModel: MovieViewModel = viewModel()
            when (page) {
                0 -> MovieScreen()
                1 -> NowPlayingScreen(
                    apiKey = "5ac3694702a27824c2bb5c54a0506de4",
                    movieViewModel = movieViewModel
                )
            }
        }
    }
}