package com.picpay.desafio.android.models


sealed class ViewEvents<out T : Any>
class SuccessRequest<out T : Any>(val data: T) : ViewEvents<T>()
object ErrorRequest : ViewEvents<Nothing>()
object SavedDataRepo : ViewEvents<Nothing>()
object CannotSaveDataRepo : ViewEvents<Nothing>()
