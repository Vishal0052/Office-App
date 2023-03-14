package com.example.officeapp.data.Admin.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.officeapp.R
import com.example.officeapp.model.PayloadX
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel


@Composable
fun DeleteMenuItem(viewModel: LoginViewModel) {

    val deletedItem = remember { mutableStateListOf<PayloadX>() }
    LaunchedEffect(key1 = true) {
        viewModel.getMenuItems()
    }
    val getMenuDetails = viewModel.getMenuItemsResponse.value


    when(getMenuDetails){
        is Resource.Success -> {
            getMenuDetails.data?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    itemsIndexed(
                        items = it.payload,
                        itemContent = {index,item->
                            AnimatedVisibility(visible = !deletedItem.contains(item),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(1000))
                            ) {
                                DeleteMenuUi(basicItems = item, deletedItem = deletedItem)
                            }
                        }
                    )
//        items(listOFData.size) { index ->
//            DeleteMenuUi(listOFData[index],deletedItem)
//        }
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
                    modifier = Modifier.padding(start = 6.dp, end = 5.dp)
                )
            }
        }
        else -> {}
    }
//    LazyColumn(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        itemsIndexed(
//            items = listOFData,
//            itemContent = {index,item->
//                AnimatedVisibility(visible = !deletedItem.contains(item),
//                enter = expandVertically(),
//                    exit = shrinkVertically(animationSpec = tween(1000))
//                ) {
//                         DeleteMenuUi(basicItems = item, deletedItem = deletedItem)
//                }
//            }
//        )
////        items(listOFData.size) { index ->
////            DeleteMenuUi(listOFData[index],deletedItem)
////        }
//    }

}

@Composable
fun DeleteMenuUi(basicItems: PayloadX, deletedItem: SnapshotStateList<PayloadX>) {


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
                                .height(200.dp)
                                .width(200.dp)
                                .align(Alignment.CenterHorizontally)
                        )


                    }

                }

                OutlinedButton(onClick = {
                                         deletedItem.add(basicItems)
                                         }
                    , shape = RoundedCornerShape(5.dp),modifier = Modifier
                        .background(Color(Constants.lightWight))
                        .align(Alignment.TopEnd)
                        .size(60.dp))
                {

                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Deletion",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(45.dp))
                }

            }

            Text(
                text = basicItems.itemName,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp, modifier = Modifier.padding(top = 5.dp)
            )
        }



    }

}