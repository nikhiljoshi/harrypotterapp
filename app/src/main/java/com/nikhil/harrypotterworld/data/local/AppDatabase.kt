package com.nikhil.harrypotterworld.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikhil.harrypotterworld.data.repository.CharactersRepository
import com.nikhil.moviesapp.data.local.dao.CharacterDetailsDao

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDetailsDao

    companion object {
        const val DB_NAME = "harry_potter_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).build()
    }
}
