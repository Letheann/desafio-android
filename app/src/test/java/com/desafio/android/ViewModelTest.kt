package com.desafio.android

import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.presentation.presentation.ViewIntent
import com.desafio.android.presentation.presentation.WelcomeViewModel
import com.example.shared.commonMain.data.dto.HPCharacter
import com.example.shared.commonMain.usecase.CharacterUseCase
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
    private var useCase: CharacterUseCase = mockk(relaxed = true)
    private lateinit var viewModel: WelcomeViewModel


    @Before
    fun setup() {
        viewModel = WelcomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given usecase returns data items when calls intent then returns loading and success state`() =
        runBlocking {
            //given
            coEvery { useCase.getCharacters() } returns flowOf(
                listOf(
                    HPCharacter(
                        name = "",
                        species = "",
                        house = "",
                        image = ""
                    )
                )
            )

            //when
            viewModel.intent(ViewIntent.GetHPCharaters)

            //then
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Loading }
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Success }

        }

    @Test
    fun `given usecase returns exception when calls intent then returns loading and error state`() =
        runBlocking {
            //given
            coEvery { useCase.getCharacters() } returns flowOf(
                listOf(
                    HPCharacter(
                        name = "",
                        species = "",
                        house = "",
                        image = ""
                    )
                )
            )

            //when
            viewModel.intent(ViewIntent.GetHPCharaters)

            //then
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Loading }
            verify(exactly = 1) { viewModel.currentState.items is ViewResource.Error }

        }
}