package com.picpay.desafio.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDto(
    val img: String?,
    val name: String?,
    @PrimaryKey
    val id: Int?,
    val username: String?
)