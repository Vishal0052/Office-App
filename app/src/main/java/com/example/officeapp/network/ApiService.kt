package com.example.officeapp.network

import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


     @POST("/user/login")
     suspend fun login(@Body loginDataModel: LoginDataModel) : Response<LoginResponse>

}