package com.example.officeapp.repository

import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.LoginResponse
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
                Resource.Error(loginResponse.body()?.message)
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

}