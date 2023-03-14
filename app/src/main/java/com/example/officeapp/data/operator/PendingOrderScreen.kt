package com.example.officeapp.data.operator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.officeapp.R
import com.example.officeapp.model.PayloadXX
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Resource


@Composable fun PendingOrderScreen(operatorViewModel: OperatorViewModel){

    var getOperatorId = operatorViewModel.sharedPreferences.getString(Constants.USER_ID,null)
    var showEmptyText by remember { mutableStateOf(false)}

    LaunchedEffect(key1 = true) {
        operatorViewModel.getAllOrder("placed")
    }
    val pendingOrderList = operatorViewModel.operatorAllOrderRes.value

    when(pendingOrderList){
        is Resource.Success -> {
            pendingOrderList.data?.let {
                //PendingOrderDisplay(it)

                it.payload.forEachIndexed { index, item ->
                    if(getOperatorId==item.orderedTo&&item.orderStatus=="placed"){
                          showEmptyText=true
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = it.payload,
                        itemContent = {index,item->
                            if(getOperatorId==item.orderedTo) {
                                PendingOrderDisplay(pendingOrder = item)
                            }
                        }
                    )
                }


                if(!showEmptyText){

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
                    text = "${pendingOrderList.message}",
                    modifier = Modifier.padding(start = 6.dp, end = 5.dp)
                )
            }
        }
        else -> {Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No Data Received",
                modifier = Modifier.padding(start = 6.dp, end = 5.dp)
            )
        }}
    }

}
@Composable fun PendingOrderDisplay(pendingOrder: PayloadXX) {

     val items = remember { pendingOrder.items.toMutableList() }
    var expandCard by remember { mutableStateOf(false) }

    if(pendingOrder.orderStatus=="placed") {
        Column {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = 1.dp
            ) {

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.bag),
                            contentDescription = "Bag Icon",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .weight(0.2f)
                        )

                        Column(
                            modifier = Modifier
                                .padding(2.dp)
                                .weight(0.8F)
                        ) {

                            Row {
                                Text(
                                    text = "Order created ",
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(start = 3.dp, top = 2.dp)
                                        .weight(0.4F)
                                )

                                Text(
                                    text = pendingOrder.createdAt,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Green,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .weight(0.4F)
                                        .padding(start = 12.dp, top = 2.dp),
                                    textAlign = TextAlign.Start
                                )
                            }

                            Row {
                                Text(
                                    text = "Order From",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .weight(0.4F)
                                        .padding(start = 2.dp),
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = pendingOrder.orderedFrom,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Green,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .weight(0.4F)
                                        .padding(start = 12.dp, top = 2.dp),
                                    textAlign = TextAlign.Start
                                )
                            }

                            Row {

                                Text(
                                    text = "Order Status",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .weight(0.4F)
                                        .padding(start = 2.dp),
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = if (pendingOrder.orderStatus == "placed") "Pending" else pendingOrder.orderStatus,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Red,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .weight(0.4F)
                                        .padding(start = 12.dp, top = 2.dp),
                                    textAlign = TextAlign.Start
                                )
                            }

                            items.forEachIndexed { index, item ->

                                Column {
                                    Row {
                                        Text(
                                            text = "Item Name",
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 2.dp),
                                            textAlign = TextAlign.Start
                                        )

                                        Text(
                                            text = item.name,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Green,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 12.dp, top = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }

                                    Row {
                                        Text(
                                            text = "Quantity",
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 2.dp),
                                            textAlign = TextAlign.Start
                                        )

                                        Text(
                                            text = item.qty.toString(),
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Green,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 12.dp, top = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }

                                }

                            }


                            AnimatedVisibility(visible = expandCard) {

                                Column {


                                    Row {
                                        Text(
                                            text = "Ordered By",
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 2.dp),
                                            textAlign = TextAlign.Start
                                        )

                                        Text(
                                            text = pendingOrder.orderedBy,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Green,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 12.dp, top = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }

                                    Row {
                                        Text(
                                            text = "Ordered To",
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 2.dp),
                                            textAlign = TextAlign.Start
                                        )

                                        Text(
                                            text = pendingOrder.orderedTo,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Green,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .weight(0.4F)
                                                .padding(start = 12.dp, top = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                        }


                    }

                    Icon(
                        imageVector = if (expandCard) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Down Arrow",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 8.dp, top = 6.dp)
                            .size(25.dp)
                            .clickable {

                                expandCard = !expandCard
                            },
                        tint = Color.DarkGray
                    )
                }
            }

            Card(shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, color = Color(Constants.Grey200)),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 1.dp)
                    .fillMaxWidth()
                    .clickable {
                    }) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 10.dp),
                    text = "Order Completed",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(Constants.Green200),
                    textAlign = TextAlign.Center
                )
            }

        }
    }


}