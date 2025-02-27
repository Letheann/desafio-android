package com.desafio.android

import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.domain.user.LogsUseCase
import com.desafio.android.presentation.logs.presentation.LogsViewModel
import com.desafio.android.presentation.logs.presentation.ViewIntent
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()
    private var useCase: LogsUseCase = mockk(relaxed = true)
    private lateinit var viewModel: LogsViewModel


    @Before
    fun setup() {
        viewModel = LogsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given usecase returns data items when calls intent then returns loading and success state`() =
        runBlocking {
            //given
            coEvery { useCase.getLogsByCache() } returns flowOf(listOf(Logs("", 0)))

            //when
            viewModel.intent(ViewIntent.UpdateUi)

            //then
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Loading }
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Success }

        }

    @Test
    fun `given usecase returns exception when calls intent then returns loading and error state`() =
        runBlocking {
            //given
            coEvery { useCase.getLogsByCache() } throws Exception()

            //when
            viewModel.intent(ViewIntent.UpdateUi)

            //then
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Loading }
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Error }

        }

    @Test
    fun `given usecase returns empty when calls intent then returns loading and empty state`() =
        runBlocking {
            //given
            coEvery { useCase.getLogsByCache() } throws Exception()

            //when
            viewModel.intent(ViewIntent.UpdateUi)

            //then
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Loading }
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Empty }

        }
}