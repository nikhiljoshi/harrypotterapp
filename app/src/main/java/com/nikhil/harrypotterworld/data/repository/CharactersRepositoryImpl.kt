package com.nikhil.harrypotterworld.data.repository

import android.util.Log
import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.harrypotterworld.data.model.toCharacter
import com.nikhil.harrypotterworld.data.api.CharactersApi
import com.nikhil.moviesapp.data.local.dao.CharacterDetailsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CharactersRepositoryImpl @Inject constructor(private val api: CharactersApi,
                                                   val characterDetailsDao: CharacterDetailsDao) :
    CharactersRepository {


    override fun getCharactersFromApi(): Flow<Resource<List<CharacterModel>>> = flow {
        emit(Resource.Loading())

        try {
            val allCharacters = api.getCharacters().map { it.toCharacter() }
            addCharactersToLocal(allCharacters)

            emit(Resource.Success(allCharacters))

        } catch (exception: IOException) {
           /* emit(
                Resource.Error(
                    message = "Couldn't reach server. Check your internet connection."
                )
            )*/
            getCharactersFromLocal()

        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.localizedMessage ?: "An unexpected error occurred"
                )
            )
        }
    }

    override fun getCharactersFromLocal(): Flow<Resource<List<CharacterModel>>> =  flow {
            Log.e("getCharactersFromLocal", characterDetailsDao.getCharacters().toString())
            val allCharacters = characterDetailsDao.getCharacters();
            emit(Resource.Success(allCharacters))

    }

    override fun addCharactersToLocal(characterEntity: List<CharacterModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDetailsDao.insertCharacters(characterEntity)

            Log.e("addCharacter", characterDetailsDao.getCharacters().toString())
        }

    }




}