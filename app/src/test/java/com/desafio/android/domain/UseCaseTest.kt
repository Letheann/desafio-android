package com.desafio.android.domain

import com.desafio.android.data.repository.LogsRepository
import com.desafio.android.domain.user.LogsUseCase
import com.desafio.android.domain.user.LogsUseCaseImpl
import com.desafio.android.CoroutinesTestRule
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
    private val repository: LogsRepository = mockk(relaxed = true)
    private lateinit var useCase: LogsUseCase
    

    @Before
    fun setup() {
        useCase = LogsUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given fetch data items when calls repository then returns list`() = runBlocking {
        useCase.getLogsByCache()
        coVerify(exactly = 1) { repository.getLogsByCache() }
    }


}