package com.nikhil.harrypotterworld.data.repository

import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.harrypotterworld.data.model.toCharacter
import com.nikhil.harrypotterworld.data.api.CharactersApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CharactersRepositoryImpl @Inject constructor(private val api: CharactersApi) :
    CharactersRepository {
    override fun getCharacters(): Flow<Resource<List<CharacterModel>>> = flow {
        emit(Resource.Loading())

        try {
            val allCharacters = api.getCharacters().map { it.toCharacter() }
            emit(Resource.Success(allCharacters))

        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.localizedMessage ?: "An unexpected error occurred"
                )
            )
        }
    }

}