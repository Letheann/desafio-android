package com.example.coredata

import com.example.coredata.dao.db.UserDB
import com.example.coredata.extensions.safeRequestCheckingNetwork
import com.example.coredata.models.User
import com.example.coredata.models.request.SuccessRequest
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

   private val response by lazy {
        listOf(
            User(1, "User 1", "avatar_url", 1),
            User(2, "User 2", "avatar_url", 2),
            User(3, "User 3", "avatar_url", 3)
        )

    }

    private val mockCall =
        mock<Call<List<User>>> {
            on { execute() } doReturn Response.success(response)
        }


    private val mockApiService = mock<PicPayService> {
        on { getUsers() } doReturn mockCall
    }

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
    fun `getUsers test for return response`() = runBlockingTest {

        var success = false

        mockApiService.stub {
            onBlocking {
                getUsers().execute()
            } doReturn Response.success(
                200,
                response
            )
        }
        repository.execute().collect {
            if (it != null) {
                success = true
                assertEquals(it.size, response.size)
            }
        }

        assertTrue(success)
    }

}