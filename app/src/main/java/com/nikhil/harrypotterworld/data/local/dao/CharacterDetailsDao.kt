package com.nikhil.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikhil.harrypotterworld.data.model.CharacterModel

@Dao
interface CharacterDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characterEntity: List<CharacterModel>)

    @Query("SELECT * FROM CharacterModel")
    fun getCharacters(): List<CharacterModel>

}
