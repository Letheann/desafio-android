package com.desafio.android.data.mapper

import com.desafio.android.core.repository.BaseMapper
import com.desafio.android.data.model.Logs
import com.desafio.android.data.model.LogsDto


object LogsMapper : BaseMapper<LogsDto, Logs>() {
    override fun transformTo(source: LogsDto): Logs =
        Logs(
            id = source.id ?: 0,
            log = source.log ?: ""
        )
}
