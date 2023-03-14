package com.example.officeapp.common

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.officeapp.R
import com.example.officeapp.model.PayloadX
import com.example.officeapp.model.createOrder.Item
import com.example.officeapp.utils.Constants

//@Preview
@Composable
fun CommonOrderScreen(item: PayloadX, onMenuClick: (Item) -> Unit = {}, index: Int) {

    var isVisible by remember { mutableStateOf(false) }

    var counter by rememberSaveable { mutableStateOf(0) }

    var quantity by rememberSaveable { mutableStateOf(0) }


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {

            AsyncImage(
                model = item.img,
                contentDescription = null,
                error = painterResource(id = R.drawable.book),
                modifier = Modifier
                    .size(200.dp)
                    .weight(0.5F)
            )


//            Image(
//                painter = rememberAsyncImagePainter(item.img),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(200.dp)
//                    .weight(0.5F)
//            )

//
//            Image(
//                painter = item.painter, contentDescription = "ItemImage",
//                contentScale = ContentScale.Fit, modifier = Modifier
//                    .size(200.dp)
//                    .weight(0.5F)
//            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5F), horizontalAlignment = Alignment.Start
            )
            {
                Text(
                    text = item.itemName,
                    fontStyle = FontStyle.Normal, textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp, modifier = Modifier
                        .padding(end = 5.dp, bottom = 5.dp, start = 3.dp)
                )

                Card(shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, color = Color(Constants.Grey200)),
                    modifier = Modifier
                        .padding(start = 3.dp, top = 10.dp)
                        .align(Alignment.Start)
                        .clickable {


                        })
                {

                    if (!isVisible) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 25.dp, vertical = 10.dp)
                                .clickable {

                                    isVisible = true
                                    counter++
                                    quantity=counter
                                },
                            text = "ADD",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(Constants.Green200),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {

                            RoundIconButton(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                onClick =
                                {
                                    if (counter > 1)
                                    {
                                        counter--
                                        quantity = counter
                                        onMenuClick.invoke(Item(item.itemName, quantity))

                                    } else
                                    {
                                        isVisible = false
                                        counter=0
                                        quantity = counter
                                        onMenuClick.invoke(Item(item.itemName, quantity))
                                    }
                                }, modifier = Modifier.padding(5.dp)
                            )

                            Text(
                                text = " ${counter}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(Constants.Green200),
                                textAlign = TextAlign.Center

                            )

                            RoundIconButton(

                                imageVector = Icons.Default.KeyboardArrowUp,
                                onClick = {

                                    Log.e("decrementi", index.toString())
                                    try {
                                        counter++
                                        quantity = counter
                                        onMenuClick.invoke(Item(item.itemName, quantity))

                                    } catch (e: Exception) {
                                        Log.e("decrementi", e.toString())
                                    }


                                }, modifier = Modifier.padding(5.dp)
                            )

                        }
                    }
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 13.dp)
        )
    }
}


