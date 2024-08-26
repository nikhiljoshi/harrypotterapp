package com.nikhil.harrypotterworld.data.repository

import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharactersFromApi(): Flow<Resource<List<CharacterModel>>>

    fun addCharactersToLocal(characterEntity: List<CharacterModel>)
    fun getCharactersFromLocal(): Flow<Resource<List<CharacterModel>>>
}