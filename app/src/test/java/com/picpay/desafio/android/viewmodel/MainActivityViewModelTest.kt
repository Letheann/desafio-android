package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.coredata.models.User
import com.example.coredata.repository.usecases.users.IGetUsers
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.example.coredata.models.request.SuccessRequest
import com.example.coredata.models.request.ViewEvents
import com.picpay.desafio.android.testrule.TestCoroutineRule
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainActivityViewModelTest {

    private val repository: IGetUsers = mock()
    private lateinit var viewModel: MainActivityViewModel
    private val observer: Observer<ViewEvents<List<User>>> = mock()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @Test
    fun `test request to set response as success`() = testCoroutineRule.runBlockingTest {

        val response = listOf(
            User(1, "User 1", "avatar_url", 1),
            User(2, "User 2", "avatar_url", 2),
            User(3, "User 3", "avatar_url", 3)
        )

        repository.stub {
            onBlocking {
                repository.execute()
            } doReturn flow {
                emit(response)
            }
        }

        viewModel = MainActivityViewModel(repository)
        viewModel.getUsers()

        assertEquals(
            SuccessRequest(response)
            ,viewModel.viewState().value
        )

    }

}