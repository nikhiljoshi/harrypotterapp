package com.nikhil.harrypotterworld.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikhil.harrypotterworld.data.model.Character
import com.nikhil.harrypotterworld.data.model.Wand
import com.nikhil.harrypotterworld.data.model.toCharacter
import com.nikhil.harrypotterworld.data.api.CharactersApi
import com.nikhil.harrypotterworld.data.repository.CharactersRepositoryImpl
import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.ui.homescreen.state.CharactersState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
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
class CharacterRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val charactersApi: CharactersApi = mockk()
    private val charactersRepository = CharactersRepositoryImpl(charactersApi)

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
    fun verifyCharactersRepository() = runTest {
        // Given
        val expectedState = CharactersState(characters = listOf(character.toCharacter()))

        coEvery { charactersApi.getCharacters() } returns listOf(character)

        // When
        val result = charactersRepository.getCharacters()
        advanceUntilIdle()
        // Then
        result.onEach {
            when (it) {
                is Resource.Success -> {
                    assertEquals(expectedState.characters, it.data)
                }
                else -> {

                }
            }
        }.launchIn(testScope)
    }

    companion object {
        private val character = Character(
            "Harry Potter",
            true,
            emptyList(),
            emptyList(),
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
            Wand("hjj",0.0,"vhj"),
            true,
            3
        )
    }
}
