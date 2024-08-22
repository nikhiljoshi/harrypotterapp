package com.nikhil.harrypotterworld.data.repository

import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(): Flow<Resource<List<CharacterModel>>>
}