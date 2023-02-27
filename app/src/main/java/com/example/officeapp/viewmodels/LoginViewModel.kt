package com.example.officeapp.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.officeapp.model.CreateUser
import com.example.officeapp.model.CreateUserResponse
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.LoginResponse
import com.example.officeapp.model.deleteUser.DeleteUserData
import com.example.officeapp.model.deleteUser.DeleteUserResponse
import com.example.officeapp.model.userData.UserDataRes
import com.example.officeapp.repository.OfficeRepository
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Constants.USER_EMAIL
import com.example.officeapp.utils.Constants.USER_PASSWORD
import com.example.officeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val officeRepository: OfficeRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    val loginResponse: MutableState<Resource<LoginResponse>?> = mutableStateOf(null)
    val createUserResponse: MutableState<Resource<CreateUserResponse>?> = mutableStateOf(null)

    val getUserResponse: MutableState<Resource<UserDataRes>?> = mutableStateOf(null)
    val deleteUserResponse: MutableState<Resource<DeleteUserResponse>?> = mutableStateOf(null)


    var email = ""
    var password = ""
    fun loginUser(loginDataModel: LoginDataModel) {
        email = loginDataModel.email
        password = loginDataModel.password
        viewModelScope.launch {
            loginResponse.value = Resource.loading()
            loginResponse.value = officeRepository.loginUser(loginDataModel)
        }
    }

    fun createUser(token: String, createUser: CreateUser) {
        viewModelScope.launch {
            createUserResponse.value = Resource.loading()
            createUserResponse.value = officeRepository.createUser(token, createUser)
        }
    }

    fun getUsers(role : String){
        viewModelScope.launch {
            getUserResponse.value = Resource.loading()
            getUserResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                ?.let { authToken ->
                    officeRepository.getUser(authToken,role)
                }
        }
    }

    fun deleteUser(deleteUserData: DeleteUserData){
        viewModelScope.launch {
            deleteUserResponse.value =Resource.loading()
            deleteUserResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                ?.let {authToken ->
                officeRepository.deleteUser(authToken,deleteUserData)

            }
        }
    }



    fun saveValueInPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).commit()
    }

    fun getValueFromPref(key: String): String? = sharedPreferences.getString(key, null)
    fun saveUserDetails() {
        sharedPreferences.edit().putString(USER_EMAIL, email).commit()
        sharedPreferences.edit().putString(USER_PASSWORD, password).commit()
    }

}