package com.example.shared.commonMain.data

internal sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val throwable: Throwable) : NetworkResponse<Nothing>()
}
