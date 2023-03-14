package com.example.officeapp.data.SubAdmin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.officeapp.Screen
import com.example.officeapp.viewmodels.LoginViewModel


@Composable
fun SubAdmin(navController: NavController,viewModel:LoginViewModel){
    Column {

        TopAppBar(title = { Text(text = "OFFICE APP") },
            actions = {
                IconButton(onClick = {
                    // Toast.makeText(context, "Back Icon Click", Toast.LENGTH_SHORT).show()
                    viewModel.sharedPreferences.edit().clear().apply()
                    navController.navigate(Screen.login.route)
                })
                {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Go Back")
                }
            }
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(Screen.SAdminCreateOrder.route)
                    },
                shape = RoundedCornerShape(13.dp),
                elevation = 1.dp, backgroundColor = Color("#e9e9eb".toColorInt())
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = com.example.officeapp.R.drawable.create_order),
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(text = "Create Order", textAlign = TextAlign.Center, fontSize = 20.sp)

                }

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        navController.navigate(Screen.SAdminOrderHistory.route)
                    }
                    .padding(10.dp),
                shape = RoundedCornerShape(13.dp),
                elevation = 1.dp, backgroundColor = Color("#e9e9eb".toColorInt())
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = com.example.officeapp.R.drawable.order_history),
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(text = "Order History", textAlign = TextAlign.Center, fontSize = 20.sp)

                }

            }

        }

    }

}