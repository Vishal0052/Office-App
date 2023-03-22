package com.example.officeapp.data.operator

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.officeapp.model.GetAllOrderResponse
import com.example.officeapp.model.orderStatus.ChangeOrderStatusData
import com.example.officeapp.model.orderStatus.ChangeOrderStatusResponse
import com.example.officeapp.repository.OfficeRepository
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperatorViewModel @Inject constructor(
    private val officeRepository: OfficeRepository,
    val sharedPreferences: SharedPreferences
   ) : ViewModel()
   {
       val operatorFilterOrderRes : MutableState<Resource<GetAllOrderResponse>?> = mutableStateOf(null)
       val operatorAllOrderRes : MutableState<Resource<GetAllOrderResponse>?> = mutableStateOf(null)
       val operatorChangeOrderRes : MutableState<Resource<ChangeOrderStatusResponse>?> = mutableStateOf(null)

       fun getFilterOrder(status : String ){
           viewModelScope.launch {
               operatorFilterOrderRes.value = Resource.loading()
               operatorFilterOrderRes.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                   ?.let { authToken ->
                       officeRepository.getFilterOrder(authToken,status)
                   }
           }

       }

       fun getAllOrder(){
           viewModelScope.launch {
               operatorAllOrderRes.value = Resource.loading()
               operatorAllOrderRes.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                   ?.let { authToken ->
                       officeRepository.getAllOrder(authToken)
                   }
           }

       }

       fun changeOrderStatus(changeOrderStatusData: ChangeOrderStatusData){
           viewModelScope.launch {
               operatorChangeOrderRes.value = Resource.loading()
               operatorChangeOrderRes.value = sharedPreferences.getString(Constants.AUTH_TOKEN,null)
                   ?.let { authToken ->
                       officeRepository.changeOrdersStatus(authToken ,changeOrderStatusData)
                   }
           }

       }


   }