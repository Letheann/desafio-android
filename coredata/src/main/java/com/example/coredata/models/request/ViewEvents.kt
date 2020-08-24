package com.example.coredata.models.request


sealed class ViewEvents<out T : Any>
data class SuccessRequest<out T : Any>(val data: T?) : ViewEvents<T>()
object ErrorRequest : ViewEvents<Nothing>()
