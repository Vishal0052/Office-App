package com.example.officeapp.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.example.officeapp.model.PayloadXXX
import com.example.officeapp.model.deleteOrder.DeleteOrderData
import com.example.officeapp.utils.Constants
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun CommonOrderHistory(item: PayloadXXX, viewModel: LoginViewModel,onCancelClick : (Boolean) -> Unit = {}) {
    val items = remember { item.items.toMutableList() }
    var expandCard by remember { mutableStateOf(false) }

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
                                text = item.createdAt,
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
                                text = item.orderedFrom,
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
                                text = if (item.orderStatus == "placed") "Pending" else item.orderStatus,
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
                                        text = item.orderedBy,
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
                                        text = item.orderedTo,
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

        if(item.orderStatus!="delivered"){
            Card(shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, color = Color(Constants.Grey200)),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 4.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.cancelOrder(DeleteOrderData(item._id))
                        onCancelClick.invoke(true)
                    }) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 10.dp),
                    text = "Cancel Order",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(Constants.Green200),
                    textAlign = TextAlign.Center
                )
            }
        }


    }
}

