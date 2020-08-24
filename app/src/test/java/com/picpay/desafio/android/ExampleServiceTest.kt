package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.example.coredata.repository.usecases.service.PicPayService
import junit.framework.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ExampleServiceTest {

    private val api = mock<PicPayService>()

    private val service = ExampleService(api)

    @Test
    fun exampleTest() {
        // given
        val call = mock<Call<List<com.example.coredata.models.User>>>()
        val expectedUsers = emptyList<com.example.coredata.models.User>()

        whenever(call.execute()).thenReturn(Response.success(expectedUsers))
        whenever(api.getUsers()).thenReturn(call)

        // when
        val users = service.example()

        // then
        assertEquals(users, expectedUsers)
    }
}