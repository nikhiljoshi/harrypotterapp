package com.nikhil.harrypotterworld.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikhil.harrypotterworld.data.model.CharacterDto
import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.moviesapp.data.local.dao.CharacterDetailsDao


@Database(
    entities = [CharacterModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    companion object {
        const val DB_NAME = "PotterDB"
    }
    abstract fun characterDetailsDao(): CharacterDetailsDao
}