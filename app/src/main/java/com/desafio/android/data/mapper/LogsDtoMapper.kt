package com.desafio.android.data.mapper

import com.desafio.android.core.repository.BaseMapper
import com.desafio.android.data.model.LogsDto


object LogsDtoMapper : BaseMapper<String, LogsDto>() {
    override fun transformTo(credentials: String): LogsDto =
        LogsDto(
            log = credentials,
            id = null
        )
}
