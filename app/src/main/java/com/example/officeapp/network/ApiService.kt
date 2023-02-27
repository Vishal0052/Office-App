package com.example.officeapp.network

import com.example.officeapp.model.*
import com.example.officeapp.model.deleteUser.DeleteUserResponse
import com.example.officeapp.model.deleteUser.DeleteUserData
import com.example.officeapp.model.userData.UserDataRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

     @GET("user/getalluser")
     suspend fun getUsers(@Header("authorization") token: String,@Query("role") role : String):Response<UserDataRes>

     @POST("user/login")
     suspend fun login(@Body loginDataModel: LoginDataModel) : Response<LoginResponse>

     @POST("user/createuser")
     suspend fun createUser(@Header("authorization") token:String , @Body createUser : CreateUser ) : Response<CreateUserResponse>

     @DELETE("user/removeuser")
     suspend fun deleteUser(@Header("authorization") token:String , @Body deleteUserData: DeleteUserData) : Response<DeleteUserResponse>

}