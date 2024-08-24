package com.nikhil.harrypotterworld.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.harrypotterworld.data.repository.GetCharactersUseCase
import com.nikhil.harrypotterworld.ui.HomeViewModel
import com.nikhil.harrypotterworld.ui.homescreen.state.CharactersState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private val getCharactersUseCase = mockk<GetCharactersUseCase>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // 2
        Dispatchers.resetMain()
    }

    @Test
    fun getCharactersSuccessTest() = runTest(testDispatcher) {
        // Given
        val expectedState = CharactersState(characters = listOf(character))

        coEvery { getCharactersUseCase.invoke() } returns flowOf(
            Resource.Success(
                listOf(
                    character
                )
            )
        )

        // When
        viewModel = HomeViewModel(getCharactersUseCase, testDispatcher)
        advanceUntilIdle()
        coVerify { getCharactersUseCase.invoke() }
        assertNotNull(viewModel.characters.value.characters)

        // Then
        assertEquals(expectedState.characters, viewModel.characters.value.characters)
    }

    @Test
    fun getCharacterErrorTest() = runTest(testDispatcher) {
        // Given
        val expectedState =
            CharactersState(error = "An unexpected error occurred", characters = listOf())

        coEvery { getCharactersUseCase.invoke() } returns flowOf(
            Resource.Error("An unexpected error occurred")
        )

        // When
        viewModel = HomeViewModel(getCharactersUseCase, testDispatcher)
        advanceUntilIdle()
        coVerify { getCharactersUseCase.invoke() }
        // Then
        assertEquals(expectedState.error, viewModel.characters.value.error)
    }


    @Test
    fun setSearchStringTest() {
        // Given
        viewModel = HomeViewModel(getCharactersUseCase, testDispatcher)
        viewModel.setSearchString("Harry")

        // Then
        assertEquals("Harry", viewModel.searchString.value)
    }

    companion object {
        private val character = CharacterModel(
            "Harry Potter",
            true,
            "djhds",
            "hdfhdf",
            "dsnd",
            "male",
            "hjds",
            true,
            false,
            "hdhd",
            "dhhd",
            "",
            "hdks",
            "dhdhd",
            "",
            true,
            3
        )
    }
}
