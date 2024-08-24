package com.nikhil.harrypotterworld.data.local.di

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Room
import com.google.gson.Gson
import com.nikhil.harrypotterworld.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton

    fun provideGson() = Gson()

   /* @Provides
    @Singleton
    fun provideTyppeConverters(gson: Gson) = Converters(gson)*/


    @Provides
    @Singleton
    fun providePotterDB(
        @ApplicationContext context: Context,
        converters: Converters
    ) : AppDatabase {
        return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java,
            "PotterDB")
            .fallbackToDestructiveMigration()
            .addTypeConverter(converters)
            .build()
    }

   // @Provides
   // @Singleton
   // fun provideContractDao(database: AppDatabase) = database.char
}