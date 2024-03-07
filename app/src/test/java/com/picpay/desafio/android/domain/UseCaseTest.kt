package com.picpay.desafio.android.domain

import com.picpay.desafio.android.CoroutinesTestRule
import com.picpay.desafio.android.data.repository.PicPayRepository
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
    private val repository: PicPayRepository = mockk(relaxed = true)
    private lateinit var useCase: PicPayUseCase
    

    @Before
    fun setup() {
        useCase = PicPayUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given fetch data items when calls repository then returns list`() = runBlocking {
        useCase.getUsers()
        coVerify(exactly = 1) { repository.getUsers() }
    }


}