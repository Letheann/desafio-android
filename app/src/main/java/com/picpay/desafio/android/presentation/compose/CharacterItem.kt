package com.picpay.desafio.android.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.picpay.desafio.android.core.presentation.ViewResource
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.presentation.MainActivityViewModel
import com.picpay.desafio.android.presentation.ViewIntent


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecyclerCompose(
    viewModel: MainActivityViewModel,
    invokeClick: () -> Unit,
    refreshItems: () -> Unit,
    disposable: () -> Unit = {}
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                if (uiState.items == null) {
                    viewModel.intent(ViewIntent.UpdateUiCharsByCache)
                }
            }
            Lifecycle.State.CREATED -> {
                viewModel.intent(ViewIntent.UpdateUiChars)
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

    val refreshing by remember { mutableStateOf(false) }
    val state = rememberPullRefreshState(refreshing, refreshItems)

    when (val states = uiState.items) {
        is ViewResource.Success -> {
            Box(Modifier.pullRefresh(state)) {
                LazyColumn(Modifier.fillMaxSize()) {
                    if (!refreshing) {
                        items(states.data) {
                            CharacterItem(it, invokeClick, viewModel)
                        }
                    }
                }
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }
        }

        is ViewResource.Loading -> {
            Indicator()
        }

        is ViewResource.Empty -> {
            CharacterEmpty()
        }

        is ViewResource.Error -> {
            CharacterError()
        }

        else -> {}
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterItem(
    character: User,
    invokeClick: () -> Unit,
    viewModel: MainActivityViewModel?
) {

    Card(onClick = { invokeClick.invoke() }) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    character.img
                ),
                contentDescription = null,
                modifier = Modifier.size(144.dp, 144.dp),
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )
            Text(
                text = character.username,
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )

        }
    }
}


@Preview
@Composable
fun CharacterItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CharacterItem(
            User(
                img = "https://avatars.githubusercontent.com/u/28567385?v=4",
                name = "Sou o Bruno",
                id = 0,
                username = "Bruno"
            ),
            { },
            null
        )
    }
}
