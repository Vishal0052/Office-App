package com.example.officeapp.data.Admin.Screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.officeapp.R
import com.example.officeapp.Screen
import com.example.officeapp.model.DeleteMenuData
import com.example.officeapp.model.PayloadX
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel


@Composable
fun DeleteMenuItem(viewModel: LoginViewModel, navController: NavController) {

    Column {
        TopAppBar(
            title = { Text(text = "Delete Menu Items") },
            navigationIcon = {

                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Admin.route)

                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icon")
                }
            }, backgroundColor = Color(0xFFD1D3D5)
        )

        val deletedItem = remember { mutableStateListOf<PayloadX>() }
        LaunchedEffect(key1 = true) {
            viewModel.getMenuItems()
        }
        val getMenuDetails = viewModel.getMenuItemsResponse.value


        when (getMenuDetails) {
            is Resource.Success -> {
                getMenuDetails.data?.let {

                    if (it.payload.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Items Not Available",
                                modifier = Modifier.padding(start = 6.dp, end = 5.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp
                            )
                        }

                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        itemsIndexed(
                            items = it.payload,
                            itemContent = { index, item ->
                                AnimatedVisibility(
                                    visible = !deletedItem.contains(item),
                                    enter = expandVertically(),
                                    exit = shrinkVertically(animationSpec = tween(1000))
                                ) {
                                    DeleteMenuUi(
                                        basicItems = item,
                                        deletedItem = deletedItem,
                                        viewModel
                                    )
                                }
                            }
                        )
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
                        text = "${getMenuDetails.message}",
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {}
        }

    }


}

@Composable
fun DeleteMenuUi(
    basicItems: PayloadX,
    deletedItem: SnapshotStateList<PayloadX>,
    viewModel: LoginViewModel
) {

    var callDeleteRes by remember { mutableStateOf(false) }

    Surface(
        elevation = 8.dp, shape = RoundedCornerShape(4.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)

        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {


                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 5.dp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 6.dp)

                )
                {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(color = Color(Constants.Grey200))
                            .padding(10.dp)

                    )
                    {

                        AsyncImage(
                            model = basicItems.img,
                            contentDescription = null,
                            error = painterResource(id = R.drawable.book),
                            modifier = Modifier
                                .padding(6.dp)
                                .height(100.dp)
                                .width(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )


                    }

                }

                OutlinedButton(onClick = {
                    basicItems.itemName?.let {
                        DeleteMenuData(it)
                    }?.let { viewModel.deleteMenuItem(it) }
                    callDeleteRes = true
                }, shape = RoundedCornerShape(5.dp), modifier = Modifier
                    .background(Color(Constants.lightWight))
                    .align(Alignment.TopEnd)
                    .size(60.dp))
                {

                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = "Deletion",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(45.dp)
                    )
                }

            }

            Text(
                text = "${basicItems.itemName}",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp, modifier = Modifier.padding(top = 5.dp)
            )
        }


    }
    if (callDeleteRes) {
        DeleteBtnResponse(loginViewModel = viewModel) {
            if (it) {
                deletedItem.add(basicItems)
            }
        }
    }

}

@Composable
fun DeleteBtnResponse(loginViewModel: LoginViewModel, checkSuccessStatus: (Boolean) -> Unit = {}) {
    var DContext = LocalContext.current
    var deleteMenuRes = loginViewModel.deleteMenuResponse.value


    LaunchedEffect(key1 = deleteMenuRes) {
        when (deleteMenuRes) {
            is Resource.Success -> {

                Toast.makeText(DContext, "${deleteMenuRes.data?.message}", Toast.LENGTH_SHORT)
                    .show()
                checkSuccessStatus.invoke(true)
            }
            is Resource.loading -> {

//                Box(
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
            }

            is Resource.Error -> {
                Toast.makeText(DContext, "${deleteMenuRes.message}", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

}