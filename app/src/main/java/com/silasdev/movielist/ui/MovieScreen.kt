import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silasdev.movielist.data.local.Movie

@Composable
fun MovieScreen() {
    val context = LocalContext.current
    val movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(context.applicationContext as Application)
    )

    val movies by movieViewModel.moviesFlow.collectAsState()

    var title by remember { mutableStateOf("") }
    var watched by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current


    Column(
        Modifier
            .padding(16.dp)
            //tirar o cursor após clicar fora:
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            focusManager.clearFocus()
        }) {
        Text("Lista de Filmes",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título do filme") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = watched, onCheckedChange = { watched = it })
            Text("Assistido")
        }

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    movieViewModel.addMovie(title, watched)
                    title = ""
                    watched = false
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Adicionar Filme")
        }

        Divider(Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onUpdate = { movieViewModel.updateMovie(it) },
                    onDelete = { movieViewModel.deleteMovie(it) }
                )
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onUpdate: (Movie) -> Unit, onDelete: (Movie) -> Unit) {
    var editTitle by remember(movie.id) { mutableStateOf(movie.title) }
    var editWatched by remember(movie.title) { mutableStateOf(movie.watched) }

    // estado derivado
    val hasChanges = editTitle != movie.title || editWatched != movie.watched
    val focusManager = LocalFocusManager.current


    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }) {
            OutlinedTextField(
                value = editTitle,
                onValueChange = { editTitle = it },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = editWatched, onCheckedChange = { editWatched = it })
                Text("Assistido")
            }
            Row {
                if (hasChanges) {
                    Button(onClick = {
                        onUpdate(movie.copy(title = editTitle, watched = editWatched))
                        focusManager.clearFocus() // <-- sai do campo de texto, cursor some

                    }) {
                        Text("Salvar")
                    }
                    Spacer(Modifier.width(8.dp))
                }
                Button(
                    onClick = { onDelete(movie) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}
