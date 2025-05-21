package com.example.shared.commonMain.data.mapper

internal abstract class BaseMapper<T, K> {

    abstract fun transformTo(source: T): K
}
