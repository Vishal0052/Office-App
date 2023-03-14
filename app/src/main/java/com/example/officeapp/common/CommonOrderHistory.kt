package com.example.officeapp.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.officeapp.R
import com.example.officeapp.utils.Constants

@Preview
@Composable
fun CommonOrderHistory() {
    Column {

        Card(
            modifier = Modifier.fillMaxWidth().padding(4.dp), shape = RoundedCornerShape(10.dp), elevation = 1.dp
        ) {

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
                            text = "Order Created At",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 3.dp, top = 2.dp)
                                .weight(0.4F)
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
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
                            text = "Order From",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 2.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
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
                            text = "Item Name",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 2.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
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
                            text = "Item Quantity",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 2.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
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
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 2.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
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
                            text = "Ordered By",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 2.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
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
                            text = "Ordered To",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 2.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "Available",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color.Green,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(0.4F)
                                .padding(start = 12.dp, top = 2.dp),
                            textAlign = TextAlign.Start
                        )
                    }
                }


            }

        }

        Card(shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, color = Color(Constants.Grey200)),
            modifier = Modifier
                .padding(4.dp).fillMaxWidth()
                .clickable {
                }){
            Text(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 10.dp)
                    ,
                text = "Cancel Order",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(Constants.Green200),
                textAlign = TextAlign.Center
            )
        }

    }

}