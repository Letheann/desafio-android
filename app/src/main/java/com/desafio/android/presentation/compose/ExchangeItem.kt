package com.desafio.android.presentation.compose

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.data.model.Exchange
import com.desafio.android.presentation.presentation.ViewIntent
import com.desafio.android.presentation.presentation.WelcomeViewModel
import com.picpay.desafio.android.presentation.compose.CharacterError


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecyclerCompose(
    viewModel: WelcomeViewModel,
    invokeClick: (id: String) -> Unit,
    disposable: () -> Unit = {}
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                if (uiState.items == null) {
                    viewModel.intent(ViewIntent.GetExchange)
                }
            }

            Lifecycle.State.CREATED -> {
                viewModel.intent(ViewIntent.GetExchange)
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
                    ExchangeItem(it, invokeClick)
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
fun ExchangeItem(
    exchange: Exchange,
    invokeClick: (id: String) -> Unit
) {
    Card(
        onClick = { invokeClick.invoke(exchange.exchangeId) },
        modifier = Modifier.padding(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = exchange.name,
                    style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primary),
                    maxLines = 1
                )
                Text(
                    text = exchange.exchangeId,
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface),
                    maxLines = 1
                )
                Text(
                    text = "Volume 1 Day USD: ${exchange.volume1dayUsd}",
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
                    maxLines = 1
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
        ExchangeItem(
            Exchange(
                name = "",
                exchangeId = "",
                website = "",
                dataStart = "",
                dataEnd = "",
                dataQuoteStart = "",
                dataQuoteEnd = "",
                dataOrderbookStart = "",
                dataOrderbookEnd = "",
                dataTradeStart = "",
                dataTradeEnd = "",
                dataSymbolsCount = 0,
                volume1hrsUsd = 0.0,
                volume1dayUsd = 0.0,
                volume1mthUsd = 0.0
            ),
            { }
        )
    }
}
