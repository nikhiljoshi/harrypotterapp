package com.nikhil.harrypotterworld.viewmodel

import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.harrypotterworld.ui.details.DetailViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailViewModelTest {

    private var viewModel: DetailViewModel = DetailViewModel()

    @Test
    fun selectCharacterTest() {
        // Given
        val expectedState = character

        // When
        viewModel.selectedCharacter(character)

        // Then
        assertEquals(expectedState, viewModel.selectedCharacter)
    }

    @Test
    fun unselectCharacterTest() {
        // When
        viewModel.unselectCharacter()

        // Then
        assertEquals(null, viewModel.selectedCharacter)
    }

    companion object {
        private val character = CharacterModel(
            "Harry Potter",
            true,
            "test",
            "trs",
            "test",
            "male",
            "test",
            true,
            false,
            "test",
            "test",
            "",
            "test",
            "test",
            "",
            true,
            3
        )
    }
}
