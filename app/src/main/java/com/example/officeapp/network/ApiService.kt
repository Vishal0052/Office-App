package com.example.officeapp.network

import com.example.officeapp.model.CreateUser
import com.example.officeapp.model.CreateUserResponse
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {


     @POST("/user/login")
     suspend fun login(@Body loginDataModel: LoginDataModel) : Response<LoginResponse>

     @POST("user/createuser")
     suspend fun createUser(@Header("authorization") token:String , @Body createUser : CreateUser ) : Response<CreateUserResponse>

}