package com.picpay.desafio.android

import com.example.coredata.repository.usecases.services.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<com.example.coredata.models.User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}