package com.nikhil.harrypotterworld.di

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Room
import com.google.gson.Gson
import com.nikhil.harrypotterworld.BuildConfig
import com.nikhil.harrypotterworld.data.api.CharactersApi
import com.nikhil.harrypotterworld.data.local.AppDatabase
import com.nikhil.harrypotterworld.data.local.AppDatabase.Companion.DB_NAME
import com.nikhil.harrypotterworld.data.repository.CharactersRepositoryImpl
import com.nikhil.harrypotterworld.data.repository.CharactersRepository
import com.nikhil.harrypotterworld.util.ApiConstants
import com.nikhil.moviesapp.data.local.dao.CharacterDetailsDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesMoshiAdapterFactory(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideCharacterApi( moshi: Moshi): CharactersApi = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(CharactersApi::class.java)

    @Provides
    @Singleton
    fun provideCharacterRepository(api: CharactersApi,characterDetailsDao:CharacterDetailsDao): CharactersRepository =
        CharactersRepositoryImpl(api,characterDetailsDao)

    @Provides
    @Singleton
    fun providesIODispatcher() = Dispatchers.IO


}
