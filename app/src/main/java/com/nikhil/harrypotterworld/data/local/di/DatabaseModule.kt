package com.nikhil.harrypotterworld.data.local.di

import android.content.Context
import androidx.room.Room
import com.nikhil.harrypotterworld.data.local.AppDatabase
import com.nikhil.harrypotterworld.data.local.AppDatabase.Companion.DB_NAME
import com.nikhil.moviesapp.data.local.dao.CharacterDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton




@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {


    @Provides
    fun provideCharacterDetailsDao(appDatabase: AppDatabase): CharacterDetailsDao {
        return appDatabase.characterDetailsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java,
            DB_NAME)
            .build()
    }

}