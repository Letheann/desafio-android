package com.desafio.android.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.data.model.Exchange
import com.desafio.android.presentation.presentation.ViewIntent
import com.desafio.android.presentation.presentation.WelcomeViewModel

@Composable
fun ExchangeDetail(
    exchangeId: String,
    viewModel: WelcomeViewModel
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(exchangeId) {
        viewModel.intent(ViewIntent.GetExchangeById(exchangeId))
    }

    when (val states = uiState.items) {
        is ViewResource.Success -> {
            states.data?.let {
                ExchangeCard(exchange = it.first())
            }
        }

        is ViewResource.Error -> {
            Text(
                text = "Erro ao carregar os detalhes.",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {}
    }
}

@Composable
fun ExchangeCard(exchange: Exchange) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = exchange.name,
                style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.primary),
                maxLines = 1
            )
            Text(
                text = "Exchange ID: ${exchange.exchangeId}",
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                maxLines = 1
            )
            Text(
                text = "Website: ${exchange.website}",
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.secondary),
                maxLines = 1
            )
            Text(
                text = "Start Date: ${exchange.dataStart}",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
                maxLines = 1
            )
            Text(
                text = "End Date: ${exchange.dataEnd}",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
                maxLines = 1
            )
            Text(
                text = "Volume 1hr (USD): ${exchange.volume1hrsUsd}",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
                maxLines = 1
            )
            Text(
                text = "Volume 1 Day (USD): ${exchange.volume1dayUsd}",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
                maxLines = 1
            )
            Text(
                text = "Volume 1 Month (USD): ${exchange.volume1mthUsd}",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun ExchangeDetailPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ExchangeCard(
            Exchange(
                exchangeId = "1234",
                website = "https://example.com",
                name = "Sample Exchange",
                dataStart = "2022-01-01",
                dataEnd = "2023-01-01",
                dataQuoteStart = "",
                dataQuoteEnd = "",
                dataOrderbookStart = "",
                dataOrderbookEnd = "",
                dataTradeStart = "",
                dataTradeEnd = "",
                dataSymbolsCount = 10,
                volume1hrsUsd = 1000.0,
                volume1dayUsd = 50000.0,
                volume1mthUsd = 1000000.0
            )
        )
    }
}
