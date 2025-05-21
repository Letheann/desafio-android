package com.example.shared.commonMain.data.mapper

import com.example.shared.commonMain.data.dto.HPCharacter
import com.example.shared.commonMain.model.HPCharacterResponse

internal object HPCharacterResponseMapper : BaseMapper<HPCharacterResponse, HPCharacter>() {
    override fun transformTo(source: HPCharacterResponse): HPCharacter =
        HPCharacter(
            name = source.name,
            species = source.species,
            house = source.house.orEmpty(),
            image = source.image.orEmpty()
        )
}
