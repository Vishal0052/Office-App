package com.example.officeapp.repository

import android.util.Log
import com.example.officeapp.model.CreateUser
import com.example.officeapp.model.CreateUserResponse
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.LoginResponse
import com.example.officeapp.model.deleteUser.DeleteUserResponse
import com.example.officeapp.model.deleteUser.DeleteUserData
import com.example.officeapp.model.userData.UserDataRes
import com.example.officeapp.network.ApiService
import com.example.officeapp.utils.Resource
import javax.inject.Inject

class OfficeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun loginUser(loginDataModel: LoginDataModel): Resource<LoginResponse> {
        return try {
            val loginResponse = apiService.login(loginDataModel)
            if (loginResponse.isSuccessful) {
                Resource.Success(loginResponse.body())
            } else {
                Resource.Error("${loginResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun createUser(token : String ,createUser: CreateUser) : Resource<CreateUserResponse>{

        return try {
            val createUserResponse = apiService.createUser(token,createUser)
         //   Log.e("error","${createUserResponse.errorBody()?.string()}")
            if(createUserResponse.isSuccessful){
                Resource.Success(createUserResponse.body())
            } else{
                Resource.Error("${createUserResponse.errorBody()?.string()}")
            }
        } catch (exception : Exception){
            Resource.Error(exception.localizedMessage)
        }

    }

    suspend fun getUser(token : String , role: String) : Resource<UserDataRes>{

        return try {

            val getUserResponse = apiService.getUsers(token , role)


            if(getUserResponse.isSuccessful){
                Resource.Success(getUserResponse.body())
            } else{
                Log.e("error","${getUserResponse.errorBody()?.string()}")
                Resource.Error("${getUserResponse.errorBody()?.string()}")
            }
        } catch (exception : Exception){
            Resource.Error(exception.localizedMessage)
        }

    }

    suspend fun deleteUser(token: String ,deleteUserData : DeleteUserData) : Resource<DeleteUserResponse>{
        return try {
            val deleteUserResponse = apiService.deleteUser(token , deleteUserData)

            if(deleteUserResponse.isSuccessful){
                Resource.Success(deleteUserResponse.body())
            } else{
                Resource.Error("${deleteUserResponse.errorBody()?.string()}")
            }
        } catch (exception : Exception){
            Resource.Error(exception.localizedMessage)
        }
    }


}