package com.example.officeapp

import com.example.officeapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Helper {
    companion object {
        fun getApiInstance(): ApiService =
            Retrofit.Builder().baseUrl("https://office.antino.ca")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
    }
}