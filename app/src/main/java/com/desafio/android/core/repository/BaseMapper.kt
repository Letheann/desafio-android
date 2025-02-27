package com.desafio.android.core.repository

abstract class BaseMapper<T, K> {

    abstract fun transformTo(source: T): K

    abstract fun transformToReverse(source: K): T

    fun transformToList(source: List<T>): List<K> {
        return source.map { src -> transformTo(src) }
    }
}
