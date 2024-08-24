package com.nikhil.harrypotterworld.ui.homescreen.state

import com.nikhil.harrypotterworld.data.model.CharacterModel


data class CharactersState(
    val isLoading: Boolean = false,
    val error: String = "",
    val characters: List<CharacterModel> = emptyList()
)
