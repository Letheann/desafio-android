package com.desafio.android.data.mapper

import com.desafio.android.core.repository.BaseMapper
import com.desafio.android.data.model.Exchange
import com.desafio.android.data.model.ExchangeDto

object ExchangeMapperDto : BaseMapper<Exchange, ExchangeDto>() {
    override fun transformTo(source: Exchange): ExchangeDto =
        ExchangeDto(
            exchangeId = source.exchangeId,
            website = source.website ?: "",
            name = source.name ?: "",
            dataStart = source.dataStart ?: "",
            dataEnd = source.dataEnd ?: "",
            dataQuoteStart = source.dataQuoteStart ?: "",
            dataQuoteEnd = source.dataQuoteEnd ?: "",
            dataOrderbookStart = source.dataOrderbookStart ?: "",
            dataOrderbookEnd = source.dataOrderbookEnd ?: "",
            dataTradeStart = source.dataTradeStart ?: "",
            dataTradeEnd = source.dataTradeEnd ?: "",
            dataSymbolsCount = source.dataSymbolsCount ?: 0,
            volume1hrsUsd = source.volume1hrsUsd ?: 0.0,
            volume1dayUsd = source.volume1dayUsd ?: 0.0,
            volume1mthUsd = source.volume1mthUsd ?: 0.0
        )

    override fun transformToReverse(source: ExchangeDto): Exchange =
        Exchange(
            exchangeId = source.exchangeId,
            website = source.website ?: "",
            name = source.name ?: "",
            dataStart = source.dataStart ?: "",
            dataEnd = source.dataEnd ?: "",
            dataQuoteStart = source.dataQuoteStart ?: "",
            dataQuoteEnd = source.dataQuoteEnd ?: "",
            dataOrderbookStart = source.dataOrderbookStart ?: "",
            dataOrderbookEnd = source.dataOrderbookEnd ?: "",
            dataTradeStart = source.dataTradeStart ?: "",
            dataTradeEnd = source.dataTradeEnd ?: "",
            dataSymbolsCount = source.dataSymbolsCount ?: 0,
            volume1hrsUsd = source.volume1hrsUsd ?: 0.0,
            volume1dayUsd = source.volume1dayUsd ?: 0.0,
            volume1mthUsd = source.volume1mthUsd ?: 0.0
        )
}
