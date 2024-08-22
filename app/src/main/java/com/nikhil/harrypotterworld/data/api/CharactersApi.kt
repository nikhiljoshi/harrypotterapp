package com.nikhil.harrypotterworld.data.api

import com.nikhil.harrypotterworld.data.model.Character
import com.nikhil.harrypotterworld.util.ApiConstants
import retrofit2.http.GET


interface CharactersApi {

    @GET(ApiConstants.END_POINTS)
    suspend fun getCharacters(): List<Character>

}
