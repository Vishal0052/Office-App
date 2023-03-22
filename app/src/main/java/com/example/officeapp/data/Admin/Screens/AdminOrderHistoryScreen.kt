package com.example.officeapp.data.Admin.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.officeapp.Screen
import com.example.officeapp.common.CommonOrderHistory
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun AdminOrderHistory(viewModel: LoginViewModel,navController: NavController) {

    Column {
        TopAppBar(title = { Text(text = "Order History")},
            navigationIcon = {

                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Admin.route)

                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icon")
                }
            }, backgroundColor = Color(0xFFD1D3D5)
        )

        var cancelClickAdminRes by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = true) {
            viewModel.getYourOrders()
        }
        when(val getAdminOrderResult = viewModel.getYourOrderResponse.value){

            is Resource.Success -> {
                getAdminOrderResult.data?.let {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = it.payload,
                            itemContent = {index,item->
                                CommonOrderHistory(item,viewModel)
                                {
                                    cancelClickAdminRes=it
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
                        text = "${getAdminOrderResult.message}",
                        modifier = Modifier.padding(start = 6.dp, end = 5.dp)
                    )
                }
            }

            is Resource.code -> {
                if(getAdminOrderResult.code==404){
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

        if(cancelClickAdminRes){
            HandleCancelClickStatesOfAdmin(viewModel,navController)
        }

    }

}
@Composable
fun HandleCancelClickStatesOfAdmin(viewModel: LoginViewModel,navController: NavController) {
    var sContext= LocalContext.current
    var cancelAdminOrderRes = viewModel.cancelOrderResponse.value
    var isLoadingAdminOrder by remember { mutableStateOf(false) }
    if(isLoadingAdminOrder) LoadingBar()

    LaunchedEffect(key1 = cancelAdminOrderRes){
        when (cancelAdminOrderRes) {
            is Resource.Success -> {

                isLoadingAdminOrder=false
                Toast.makeText(sContext, "${cancelAdminOrderRes.data?.message}", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
                navController.navigate(Screen.Admin.route)
            }
            is Resource.loading -> {
                isLoadingAdminOrder=true
            }
            is Resource.Error -> {
                isLoadingAdminOrder=false
                Toast.makeText(sContext, "${cancelAdminOrderRes.message}", Toast.LENGTH_SHORT).show()

            }

            is Resource.code -> {
                if(cancelAdminOrderRes.code==404){
                    Toast.makeText(sContext, "${cancelAdminOrderRes.code} Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }

            else -> {}
        }
    }



}