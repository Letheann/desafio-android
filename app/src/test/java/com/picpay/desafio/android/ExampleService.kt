package com.picpay.desafio.android

import com.picpay.desafio.android.core.repository.PicPayService
import com.picpay.desafio.android.data.model.UserDto

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<UserDto> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}