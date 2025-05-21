package com.desafio.android.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.presentation.presentation.ViewIntent
import com.desafio.android.presentation.presentation.WelcomeViewModel
import com.example.shared.commonMain.data.dto.HPCharacter
import com.picpay.desafio.android.presentation.compose.CharacterError


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecyclerCompose(
    viewModel: WelcomeViewModel,
    invokeClick: (id: String) -> Unit,
    disposable: () -> Unit = {}
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                if (uiState.items == null) {
                    viewModel.intent(ViewIntent.GetHPCharaters)
                }
            }

            Lifecycle.State.CREATED -> {
                viewModel.intent(ViewIntent.GetHPCharaters)
            }

            else -> {}
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(viewModel)
        onDispose {
            lifecycle.removeObserver(viewModel)
            disposable.invoke()
        }
    }

    when (val states = uiState.items) {
        is ViewResource.Success -> {
            LazyColumn(Modifier.fillMaxSize()) {
                items(states.data) {
                    HPChar(it, invokeClick)
                }

            }
        }

        is ViewResource.Loading -> {
            Indicator()
        }

        is ViewResource.Error -> {
            CharacterError()
        }

        else -> {}
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HPChar(
    char: HPCharacter,
    invokeClick: (species: String) -> Unit
) {
    Card(
        onClick = { invokeClick(char.species) },
        modifier = Modifier.padding(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(char.image),
                contentDescription = char.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = char.name,
                    style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primary),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Species: ${char.species}",
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "House: ${char.house}",
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview
@Composable
fun ExchangeItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HPChar(
            HPCharacter(
                name = "",
                species = "",
                house = "",
                image = "",
            ),
            invokeClick = { }
        )
    }
}
