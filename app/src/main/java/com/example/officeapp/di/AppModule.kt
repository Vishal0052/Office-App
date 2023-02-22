package com.example.officeapp.di

import com.example.officeapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideApiService(): ApiService = Retrofit.Builder().baseUrl("https://office.antino.ca")
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(ApiService::class.java)
}