package com.example.officeapp.viewmodels

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.officeapp.model.*
import com.example.officeapp.model.addMenu.AddMenuData
import com.example.officeapp.model.addMenu.AddMenuResponse
import com.example.officeapp.model.createOrder.CreateOrderData
import com.example.officeapp.model.createOrder.CreateOrderDataResponse
import com.example.officeapp.model.deleteOrder.DeleteOrderData
import com.example.officeapp.model.deleteOrder.DeleteOrderResponse
import com.example.officeapp.model.deleteUser.DeleteUserData
import com.example.officeapp.model.deleteUser.DeleteUserResponse
import com.example.officeapp.model.forgetPassword.ForgetPasswordData
import com.example.officeapp.model.forgetPassword.ForgetPasswordResponse
import com.example.officeapp.model.resetPassword.ResetPasswordData
import com.example.officeapp.model.resetPassword.ResetPasswordResponse
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
     val sharedPreferences: SharedPreferences
) : ViewModel() {

    val loginResponse: MutableState<Resource<LoginResponse>?> = mutableStateOf(null)
    val createUserResponse: MutableState<Resource<CreateUserResponse>?> = mutableStateOf(null)

    val getUserResponse: MutableState<Resource<UserDataRes>?> = mutableStateOf(null)
    val deleteUserResponse: MutableState<Resource<DeleteUserResponse>?> = mutableStateOf(null)

    val deleteMenuResponse: MutableState<Resource<DeleteMenuResponse>?> = mutableStateOf(null)

    val addMenuResponse: MutableState<Resource<AddMenuResponse>?> = mutableStateOf(null)

    val getMenuItemsResponse: MutableState<Resource<GetMenuResponse>?> = mutableStateOf(null)

    val createOrderResponse: MutableState<Resource<CreateOrderDataResponse>?> = mutableStateOf(null)
    val cancelOrderResponse: MutableState<Resource<DeleteOrderResponse>?> = mutableStateOf(null)

    val resetResponse: MutableState<Resource<ResetPasswordResponse>?> = mutableStateOf(null)

    val getYourOrderResponse : MutableState<Resource<GetYourOrderResponse>?> = mutableStateOf(null)

    val forgetPasswordResponse: MutableState<Resource<ForgetPasswordResponse>?> = mutableStateOf(null)

    var email = ""
    var password = ""
    fun loginUser(loginDataModel: LoginDataModel) {
        email = loginDataModel.email
        password = loginDataModel.password
        viewModelScope.launch {
            loginResponse.value = Resource.loading()
            loginResponse.value = officeRepository.loginUser(loginDataModel)
            Log.e("check",loginResponse.toString())
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
                officeRepository.deleteUser(authToken,deleteUserData)            }
        }
    }

    fun getMenuItems() {
        viewModelScope.launch {
            getMenuItemsResponse.value = Resource.loading()
            getMenuItemsResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN, null)
                ?.let { authToken ->
                    officeRepository.getMenuItems(authToken)
                }
        }
    }

    fun createOrder(createOrderData: CreateOrderData) {
        viewModelScope.launch {
            createOrderResponse.value = Resource.loading()
            createOrderResponse.value =  sharedPreferences.getString(Constants.AUTH_TOKEN, null)
                ?.let { authToken ->
                    officeRepository.createOrders(authToken, createOrderData)
                }
        }
    }

    fun cancelOrder(deleteOrderData: DeleteOrderData){
        viewModelScope.launch {
            cancelOrderResponse.value =Resource.loading()
            cancelOrderResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                ?.let {authToken ->
                    officeRepository.deleteOrder(authToken,deleteOrderData)
                }
        }
    }

    fun getYourOrders(){
        viewModelScope.launch {
            getYourOrderResponse.value=Resource.loading()
            getYourOrderResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                ?.let {token ->
                    officeRepository.getYourOrders(token)
                }
        }
    }

    fun resetPass(resetPasswordData: ResetPasswordData) {
        viewModelScope.launch {
            resetResponse.value = Resource.loading()
            resetResponse.value =  sharedPreferences.getString(Constants.AUTH_TOKEN, null)
                ?.let { authToken ->
                    officeRepository.resetPassword(authToken,resetPasswordData)
                }
        }
    }

    fun deleteMenuItem(deleteMenuData: DeleteMenuData){
        viewModelScope.launch {
            deleteMenuResponse.value =Resource.loading()
            deleteMenuResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                ?.let {authToken ->
                    officeRepository.deleteMenuItem(authToken,deleteMenuData)
                }
        }
    }
    fun addMenuItem(addMenuData: AddMenuData){
        viewModelScope.launch {
            addMenuResponse.value =Resource.loading()
            addMenuResponse.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                ?.let {authToken ->
                    officeRepository.addMenu(authToken,addMenuData)
                }
        }
    }

    fun forgetPassword(forgetPasswordData: ForgetPasswordData) {
        viewModelScope.launch {
            forgetPasswordResponse.value = Resource.loading()
            forgetPasswordResponse.value = officeRepository.resetUser(forgetPasswordData)

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