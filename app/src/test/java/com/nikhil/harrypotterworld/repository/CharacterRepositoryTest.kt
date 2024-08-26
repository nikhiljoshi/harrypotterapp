package com.nikhil.harrypotterworld.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikhil.harrypotterworld.data.model.CharacterDto
import com.nikhil.harrypotterworld.data.model.Wand
import com.nikhil.harrypotterworld.data.model.toCharacter
import com.nikhil.harrypotterworld.data.api.CharactersApi
import com.nikhil.harrypotterworld.data.repository.CharactersRepositoryImpl
import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.ui.homescreen.state.CharactersState
import com.nikhil.moviesapp.data.local.dao.CharacterDetailsDao
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
    private val characterDetailsDao = mockk<CharacterDetailsDao>()
    private val charactersRepository = CharactersRepositoryImpl(charactersApi,characterDetailsDao)

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
        val expectedState = CharactersState(characters = listOf(characterDto.toCharacter()))

        coEvery { charactersApi.getCharacters() } returns listOf(characterDto)

        // When
        val result = charactersRepository.getCharactersFromApi()
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
        private val characterDto = CharacterDto(
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
