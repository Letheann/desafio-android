package com.example.coredata.models.request

import com.example.coredata.models.User


sealed class ViewEvents<out T : Any>
data class SuccessRequest<out T : Any>(val data: T) : ViewEvents<T>()
object ErrorRequest : ViewEvents<Nothing>()
object LoadingRequest : ViewEvents<Nothing>()
object CompletedRequest : ViewEvents<Nothing>()
