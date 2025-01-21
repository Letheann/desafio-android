package com.desafio.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class LogsDto(
    val log: String?,
    @PrimaryKey
    val id: Int?
)