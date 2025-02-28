package com.desafio.android.domain

import com.desafio.android.CoroutinesTestRule
import com.desafio.android.data.repository.ExchangeRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UseCaseTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()
    private val repository: ExchangeRepository = mockk(relaxed = true)
    private lateinit var useCase: ExchangeUseCase
    

    @Before
    fun setup() {
        useCase = ExchangeUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given fetch data items when calls repository then returns list`() = runBlocking {
        useCase.getExchanges()
        coVerify(exactly = 1) { repository.getDataFromApi() }
    }


}