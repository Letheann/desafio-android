package com.example.coredata

import com.example.coredata.dao.db.UserDB
import com.example.coredata.extensions.safeRequestCheckingNetwork
import com.example.coredata.models.User
import com.example.coredata.models.request.SuccessRequest
import com.example.coredata.models.request.ViewEvents
import com.example.coredata.repository.usecases.services.PicPayService
import com.example.coredata.repository.usecases.users.GetUsers
import com.example.coredata.utils.Utils
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Call
import retrofit2.Response


class GetUsersTest {

    private val service = mock<PicPayService>()


    private val dao = mock<UserDB>()
    private val utils = mock<Utils>()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private val repository =
        GetUsers(service, utils, dao, testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test getUsers for valid response`() = runBlockingTest {

        // Mock service
        val response = listOf(
            User(1, "User 1", "avatar_url", 1, "user_name"),
            User(2, "User 2", "avatar_url", 2, "user_name"),
            User(3, "User 3", "avatar_url", 3, "user_name")
        )

        utils.stub {
            onBlocking { checkNetworkState() } doReturn true
        }

        service.stub {
            onBlocking { getUsers() } doReturn Response.success(200, response)
        }

        // Test
        val flow = repository.execute()

        // Verify
        var success = false

        flow.collect { result: ViewEvents<List<User>> ->
            when (result) {
                is SuccessRequest<List<User>> -> {
                    success = true
                    assertEquals(result.data.size, response.size)
                }
            }
        }

        assertTrue(success)
    }

}