package com.example.officeapp.data.SubAdmin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.officeapp.R
import com.example.officeapp.common.CommonButton
import com.example.officeapp.common.CommonOrderScreen
import com.example.officeapp.common.CommonTf
import com.example.officeapp.model.userData.Payload

data class OperatorAvailability(var name : String , var Availability : String)

data class MenuItems(var painter: Painter, val name : String)

@Composable
fun SAdminCreateOrder() {



    var listOfOperator = listOf(

        OperatorAvailability("Yusuf","Available"),
        OperatorAvailability("Ramesh","Not Available"),
        OperatorAvailability("BISNS","Available")
    )




    var context = LocalContext.current

    var CavinName by remember { mutableStateOf("") }

    var selectOperator by remember { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }

    var ExpandedIcon =if (isExpanded){
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {

            CommonTf(text = CavinName, label = "Enter Cavin Name", onTextChange = {
                CavinName = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp))


            OutlinedTextField(value = selectOperator, onValueChange = {
                selectOperator = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                ,enabled = false,
                trailingIcon = {

                    Icon(ExpandedIcon, contentDescription = null, Modifier.clickable {
                        isExpanded = !isExpanded
                    })

                },
                label = { Text(text = "Select Role") }

            )

            if (isExpanded) {
                LazyColumn {
                    items(items = listOfOperator) {
//                        OperatorUi(item = it) {
//                            selectOperator = it
//                            isExpanded = false
                        }
                    }
                }
            }

//            LazyColumn(modifier = Modifier.weight(1f))
//            {
//                items(items = listOFMenu){
//                    CommonOrderScreen(item = it)
//                }
//            }

//            CommonButton(modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
//                onClickBtn = {
//                    Toast.makeText( context, "Button Clicked", Toast.LENGTH_SHORT).show()
//                })
//        }
    }



