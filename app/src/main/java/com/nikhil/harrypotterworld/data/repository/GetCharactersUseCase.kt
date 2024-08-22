package com.nikhil.harrypotterworld.data.repository

import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.harrypotterworld.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(): Flow<Resource<List<CharacterModel>>> {
        return repository.getCharacters()
    }
}