package com.desafio.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange")
data class ExchangeDto(
    @PrimaryKey
    val exchangeId: String,
    val website: String?,
    val name: String?,
    val dataStart: String?,
    val dataEnd: String?,
    val dataQuoteStart: String?,
    val dataQuoteEnd: String?,
    val dataOrderbookStart: String?,
    val dataOrderbookEnd: String?,
    val dataTradeStart: String?,
    val dataTradeEnd: String?,
    val dataSymbolsCount: Int?,
    val volume1hrsUsd: Double?,
    val volume1dayUsd: Double?,
    val volume1mthUsd: Double?
)
