package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coredata.models.User
import com.example.coredata.models.request.CompletedRequest
import com.example.coredata.models.request.ErrorRequest
import com.example.coredata.models.request.LoadingRequest
import com.example.coredata.models.request.SuccessRequest
import com.example.coredata.repository.usecases.users.IGetUsers
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.picpay.desafio.android.testrule.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainActivityViewModelTest {

    private val repository: IGetUsers = mock()
    private lateinit var viewModel: MainActivityViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test request to set response as success`() = testCoroutineRule.runBlockingTest {
        repository.stub {
            onBlocking {
                repository.execute()
            } doReturn flow {
                emit(SuccessRequest(listOf(User())))
            }
        }
        viewModel = MainActivityViewModel(repository)
        assert(
            viewModel.viewState().value is SuccessRequest
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test request to set response as error`() = testCoroutineRule.runBlockingTest {
        repository.stub {
            onBlocking {
                repository.execute()
            } doReturn flow {
                emit(ErrorRequest)
            }
        }
        viewModel = MainActivityViewModel(repository)
        assert(viewModel.viewState().value is ErrorRequest)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test request to set response as Loading`() = testCoroutineRule.runBlockingTest {
        repository.stub {
            onBlocking {
                repository.execute()
            } doReturn flow {
                emit(LoadingRequest)
            }
        }
        viewModel = MainActivityViewModel(repository)
        assert(viewModel.viewState().value is LoadingRequest)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test request to set response as Completed`() = testCoroutineRule.runBlockingTest {
        repository.stub {
            onBlocking {
                repository.execute()
            } doReturn flow {
                emit(CompletedRequest)
            }
        }
        viewModel = MainActivityViewModel(repository)
        assert(viewModel.viewState().value is CompletedRequest)
    }


}