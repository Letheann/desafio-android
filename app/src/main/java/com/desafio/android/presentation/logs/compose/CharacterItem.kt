package com.desafio.android.presentation.logs.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.data.model.Logs
import com.desafio.android.presentation.logs.presentation.LogsViewModel
import com.desafio.android.presentation.logs.presentation.ViewIntent


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecyclerCompose(
    viewModel: LogsViewModel,
    invokeClick: () -> Unit,
    disposable: () -> Unit = {}
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                viewModel.intent(ViewIntent.UpdateUi)
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
                    CharacterItem(it, invokeClick)
                }
            }
        }

        else -> {}
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterItem(
    log: Logs,
    invokeClick: () -> Unit
) {
    Card(onClick = { invokeClick.invoke() }) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = log.log,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
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
            Logs(
                id = 0,
                log = "{}"
            )
        ) { }
    }
}
