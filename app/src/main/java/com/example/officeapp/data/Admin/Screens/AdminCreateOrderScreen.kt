package com.example.officeapp.data.Admin.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.officeapp.Screen
import com.example.officeapp.common.CommonButton
import com.example.officeapp.common.CommonOrderScreen
import com.example.officeapp.common.CommonTf
import com.example.officeapp.model.createOrder.CreateOrderData
import com.example.officeapp.model.createOrder.Item
import com.example.officeapp.model.userData.Payload
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun AdminCreateOrderScreen(
    navController: NavHostController,
    viewModel: LoginViewModel
) {


    val Context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.getUsers("OPERATOR")
    }

    LaunchedEffect(key1 = true) {
        viewModel.getMenuItems()
    }

    val getOperator = viewModel.getUserResponse.value
    Log.e("oper",getOperator.toString())

    val getMenuItemData = viewModel.getMenuItemsResponse.value
    Log.e("res", getMenuItemData.toString())


    var CavinName by remember { mutableStateOf("") }

    var selectOperator by remember { mutableStateOf("") }

    var selectOperatorId by remember { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }

    var oApiCall by remember { mutableStateOf(false) }

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
                text = CavinName, label = "Enter Cavin Name", onTextChange = {
                    CavinName = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            )


            OutlinedTextField(value = selectOperator, onValueChange = {
                selectOperator = it

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp), enabled = false,
                trailingIcon = {

                    Icon(ExpandedIcon, contentDescription = null, Modifier.clickable {
                        isExpanded = !isExpanded
                    })

                },
                label = { Text(text = "Select Operator") }

            )

            if (isExpanded) {

                when (getOperator) {

                    is Resource.Success -> {
                        getOperator.data?.let {

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
                                        selectOperator = it
                                        selectOperatorId = it
                                        isExpanded = false
                                    }

                                    //Log.e("checkdata",it.payload.toString())
                                })
                            }

                            if(it.payload.isNullOrEmpty()){
                                Text(
                                    text = "No Operator Available",
                                    modifier = Modifier.padding(start = 6.dp, end = 5.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 25.sp
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(Context, "Something went wrong ${getOperator.message}", Toast.LENGTH_SHORT).show()
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

            CommonButton(text = "Order Placed", onClickBtn = {
                if(CavinName.isBlank()  || selectOperator.isBlank()){
                    Toast.makeText(Context, "Filled Cant be Blank", Toast.LENGTH_SHORT).show()
                }
                else{

                    menuItemsList.clear()
                    val size = menuItemsHashMap.size
                    menuItemsHashMap.forEach { index, item ->
                        menuItemsList.add(item)
                        if (size == menuItemsList.size) {

                            scope.launch {
                                viewModel.createOrder(
                                    CreateOrderData(
                                        menuItemsList,
                                        CavinName,
                                        selectOperatorId
                                    )
                                )
                            }

                            oApiCall = true

                        }
                    }
                }

            }, modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 16.dp))

            if (oApiCall) {
                CreateApiCall(viewModel = viewModel, navController)
            }

        }
    }

}

@Composable
fun CreateApiCall(
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
                    navController.navigate(Screen.Admin.route)


                }
            }
            is Resource.loading -> {

                isLoading = true

            }
            is Resource.Error -> {
                navController.popBackStack()
                navController.navigate(Screen.Admin.route)
                isLoading = false
            }
            else -> {}
        }
    }


}

@Composable
fun OperatorUi(
    operator: Payload,
    onItemClick: (String) -> Unit = {},
    getOperatorId: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(operator.name)
                    getOperatorId(operator._id)
                }, horizontalArrangement = Arrangement.Start
        ) {

            Text(
                text = operator.name,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, modifier = Modifier.weight(0.5F)
            )

            Text(
                text = if (operator.isAvailable) "Available" else "Not Available",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = Color.Green,
                fontSize = 18.sp, modifier = Modifier
                    .weight(0.5F)
                    .padding(start = 3.dp), textAlign = TextAlign.Start
            )

        }

    }

}

@Composable
fun LoadingBar() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator()
    }
}


