package com.example.officeapp.data.SubAdmin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.officeapp.Screen
import com.example.officeapp.common.CommonButton
import com.example.officeapp.common.CommonOrderScreen
import com.example.officeapp.common.CommonTf
import com.example.officeapp.data.Admin.Screens.LoadingBar
import com.example.officeapp.data.Admin.Screens.OperatorUi
import com.example.officeapp.model.createOrder.CreateOrderData
import com.example.officeapp.model.createOrder.Item
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun SAdminCreateOrder(navController: NavHostController, viewModel: LoginViewModel) {
    //AdminCreateOrderScreen(navController = navController, viewModel = viewModel)

    val Context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.getUsers("OPERATOR")
    }

    LaunchedEffect(key1 = true) {
        viewModel.getMenuItems()
    }


    val getOperators = viewModel.getUserResponse.value
    Log.e("oper",getOperators.toString())


    val getMenuItemData = viewModel.getMenuItemsResponse.value

    var SAdminCavinName by remember { mutableStateOf("") }

    var selectedOperator by remember { mutableStateOf("") }

    var selectedOperatorId by remember { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }

    var oApiCallResponse by remember { mutableStateOf(false) }

    val menuItemsList = remember { mutableStateListOf<Item>() }

    val menuItemsHashMap = remember { mutableStateMapOf<Int, Item>() }

    var ExpandedIcon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CommonTf(
                text = SAdminCavinName, label = "Enter Cavin Name", onTextChange = {
                    SAdminCavinName = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            )


            OutlinedTextField(value = selectedOperator, onValueChange = {
                selectedOperator = it

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp), enabled = false,
                trailingIcon = {

                    Icon(ExpandedIcon, contentDescription = null, Modifier.clickable {
                        isExpanded = !isExpanded
                    })

                },
                label = { Text(text = "Select Role") }

            )

            if (isExpanded) {

                when (getOperators) {


                    is Resource.Success -> {
                        getOperators.data?.let {

//                            listOfOperator =
//
//                            Log.e("checkdata",listOfOperator.toString())
                            LazyColumn {
//                                items(items = it.payload){
//                                    OperatorUi(item = it){
//
//                                        selectOperator=it
//                                        isExpanded=false
//                                    }
//                                }

                                itemsIndexed(items = it.payload, itemContent = { index, item ->
                                    OperatorUi(operator = item) {
                                        selectedOperator = it
                                        selectedOperatorId = it
                                        isExpanded = false
                                    }

                                    //Log.e("checkdata",it.payload.toString())
                                })
                            }

                            if(it.payload.isNullOrEmpty()){
                                Text(
                                    text = "No Operator Avaialable",
                                    modifier = Modifier.padding(start = 6.dp, end = 5.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 25.sp
                                )
                            }

                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(Context, "${getOperators.message}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }

            when (getMenuItemData) {

                is Resource.Success -> {
                    getMenuItemData.data?.let {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 10.dp)
                        ) {
//                            items(items = it.payload){
//                                CommonOrderScreen(item = it)
//
//                            }
                            itemsIndexed(items = it.payload, itemContent = { Index, item ->
                                CommonOrderScreen(item = item, index = Index, onMenuClick = {
                                    if (it.qty == 0)
                                        menuItemsHashMap.remove(Index)
                                    else
                                        menuItemsHashMap[Index] = it
                                })
                            })
                        }

                        if(it.payload.isEmpty()){

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
                            text = "${getMenuItemData.message}",
                            modifier = Modifier.padding(start = 6.dp, end = 5.dp)
                        )
                    }
                }

                else -> {}
            }


            // CommonOrderScreen(it)


            CommonButton(text = "Order Placed", onClickBtn = {
                menuItemsList.clear()
                val size = menuItemsHashMap.size
                menuItemsHashMap.forEach { index, item ->
                    menuItemsList.add(item)
                    if (size == menuItemsList.size) {

                        scope.launch {
                            viewModel.createOrder(
                                CreateOrderData(
                                    menuItemsList,
                                    SAdminCavinName,
                                    selectedOperatorId
                                )
                            )
                        }

                        oApiCallResponse = true

                    }

                }

            }, modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 16.dp))

            if (oApiCallResponse) {
                CreateOrderApiCall(viewModel = viewModel, navController)
            }

//        Button(onClick = {
////            Log.e("incremente", menuItemsList.forEach {
////                Log.e("vishal", it.toString())
////            }.toString())
//
//            menuItemsList.clear()
//             val size = menuItemsHashMap.size
//            Log.v("Size",size.toString())
//            menuItemsHashMap.forEach { index, item ->
//                    menuItemsList.add(item)
//                if(size == menuItemsList.size)
//                {
//
//                }
//                Log.v("Sizeh",menuItemsList.size.toString())
//            }
//
//
//                // if(size == menuItemsList.size)
//            menuItemsList.forEachIndexed { index, item ->
//                Log.e("vishal1${index}",menuItemsList.get(index).toString())
//            }
//
//        })
//        {
//            Text(text = "click")
//        }

        }
    }


}
@Composable
fun CreateOrderApiCall(
    viewModel: LoginViewModel,
    navController: NavHostController,

    ) {
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(false) }

    if (isLoading) LoadingBar()
    val createOrderResult = viewModel.createOrderResponse.value

    LaunchedEffect(key1 = createOrderResult) {

        when (createOrderResult) {
            is Resource.Success -> {
                isLoading = false
                createOrderResult.data?.let {
                    Toast.makeText(context, "Order Placed Succesfully", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    navController.navigate(Screen.SubAdmin.route)
                }
            }
            is Resource.loading -> {
                isLoading = true
            }
            is Resource.Error -> {
                navController.popBackStack()
                navController.navigate(Screen.SubAdmin.route)
                isLoading = false
            }
            else -> {}
        }
    }


}




