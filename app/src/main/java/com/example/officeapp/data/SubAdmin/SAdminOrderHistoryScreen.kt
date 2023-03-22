package com.example.officeapp.data.SubAdmin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.officeapp.Screen
import com.example.officeapp.common.CommonOrderHistory
import com.example.officeapp.data.Admin.Screens.LoadingBar
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun SAdminOrderHistory(viewModel: LoginViewModel,navController: NavController){

    var cancelClickRes by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.getYourOrders()
    }
    val getYourOrderResult = viewModel.getYourOrderResponse.value
    when(getYourOrderResult){

        is Resource.Success -> {
            getYourOrderResult.data?.let {

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = it.payload,
                        itemContent = {index,item->
                            CommonOrderHistory(item,viewModel){
                                cancelClickRes=it
                            }

                        }
                    )
                }

                if(it.message=="no records found!"){

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "No records found!",
                            modifier = Modifier.padding(start = 6.dp, end = 5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp
                        )
                    }


                }

                if(it.payload.isNullOrEmpty()){

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "No Data Received",
                            modifier = Modifier.padding(start = 6.dp, end = 5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp
                        )
                    }


                }

            }
        }
        is Resource.loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "${getYourOrderResult.message}",
                    modifier = Modifier.padding(start = 6.dp, end = 5.dp)
                )
            }
        }

        is Resource.code -> {
            if(getYourOrderResult.code==404){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "No Data Received",
                        modifier = Modifier.padding(start = 6.dp, end = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )
                }
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No Data Received",
                    modifier = Modifier.padding(start = 6.dp, end = 5.dp)
                )
            }
        }


    }

    if(cancelClickRes){
        HandleCancelClickStates(viewModel,navController)
    }
}

@Composable
fun HandleCancelClickStates(viewModel: LoginViewModel,navController: NavController) {
    var SContext= LocalContext.current
    var cancelOrderRes = viewModel.cancelOrderResponse.value
    var isLoadingOrder by remember { mutableStateOf(false) }
    if(isLoadingOrder) LoadingBar()

    LaunchedEffect(key1 = cancelOrderRes){
        when (cancelOrderRes) {
            is Resource.Success -> {

                isLoadingOrder=false
                Toast.makeText(SContext, "${cancelOrderRes.data?.message}", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
                navController.navigate(Screen.SubAdmin.route)
            }
            is Resource.loading -> {
                isLoadingOrder=true
            }
            is Resource.Error -> {
                isLoadingOrder=false
                Toast.makeText(SContext, "${cancelOrderRes.message}", Toast.LENGTH_SHORT).show()
            }

            is Resource.code -> {
                if(cancelOrderRes.code==404){
                    Toast.makeText(SContext, "${cancelOrderRes.code} Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }

            else -> {}
        }
    }



}


