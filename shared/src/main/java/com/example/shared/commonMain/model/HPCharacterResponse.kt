package com.example.shared.commonMain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class HPCharacterResponse(
    val name: String,
    val species: String,
    val house: String? = null,
    val image: String? = null
)