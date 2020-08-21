package com.picpay.desafio.android

import com.example.coredata.models.User
import com.example.coredata.repository.PicPayService

class ExampleService(
    private val service: com.example.coredata.repository.PicPayService
) {

    fun example(): List<com.example.coredata.models.User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}