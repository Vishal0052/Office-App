package com.example.officeapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.LoginResponse
import com.example.officeapp.repository.OfficeRepository
import com.example.officeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val officeRepository: OfficeRepository) : ViewModel() {
    val loginResponse : MutableState<Resource<LoginResponse>> = mutableStateOf(Resource.loading())

    fun loginUser(loginDataModel: LoginDataModel){
       viewModelScope.launch {
           loginResponse.value = officeRepository.loginUser(loginDataModel)

       }
    }
}