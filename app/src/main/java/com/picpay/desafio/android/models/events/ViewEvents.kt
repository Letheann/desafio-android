package com.picpay.desafio.android.models.events

import com.picpay.desafio.android.models.User


sealed class ViewEvents {
    data class SuccessGetUsers(val users: List<User>) : ViewEvents()
    object ErrorRequest : ViewEvents()
    object SavedDataRepo : ViewEvents()
    object CannotSaveDataRepo : ViewEvents()
}