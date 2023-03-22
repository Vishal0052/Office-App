package com.example.officeapp

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.officeapp.common.AleartDialogBox
import com.example.officeapp.utils.Constants
import com.example.officeapp.viewmodels.LoginViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


@Composable
fun AdminData(navController: NavController,viewModel: LoginViewModel){


    var ItemList = listOf(GridModel("Add Users",painterResource(R.drawable.add)),
        GridModel("Users Details",painterResource(R.drawable.all_users)),
        GridModel("Delete Menu",painterResource(R.drawable.delete_menu)),
        GridModel("Add Menu Item",painterResource(R.drawable.add_menu)),
        GridModel("Create Order",painterResource(R.drawable.create_order)),
        GridModel("Order History",painterResource(R.drawable.order_history))
    )

    val context = LocalContext.current

    Column() {
        val openDialog = remember { mutableStateOf(false) }

        TopAppBar(title = { Text(text = "OFFICE APP") },
            actions = {
                IconButton(onClick = {
                    // Toast.makeText(context, "Back Icon Click", Toast.LENGTH_SHORT).show()
                    openDialog.value=true

                })
                {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Go Back")
                }
            }, backgroundColor = Color(0xFFD1D3D5)
        )

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
        , modifier = Modifier.padding(start = 18.dp,top=30.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
                ) {
                Text(text = "Hii ", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "${viewModel.sharedPreferences.getString(Constants.USER_NAME,"Antino")}",
                    fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Image(painter = painterResource(id = R.drawable.hand), contentDescription = "hand icon",
                    modifier = Modifier.height(25.dp).width(25.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,modifier = Modifier.padding(top = 6.dp)) {
                Text(text = "Welcome to ", fontSize = 17.sp, fontWeight = FontWeight.Light)
                Text(text = "Antino Office App", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }
        }



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {


            LazyVerticalGrid(columns = GridCells.Fixed(2)
            ){
                items(ItemList){

                    Card(shape = RoundedCornerShape(10.dp), backgroundColor = Color("#FF03DAC5".toColorInt()),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    if (it.itemName == "Add Users") {
                                        navController.navigate(Screen.AddSubAdmin.route)
                                    } else if (it.itemName == "Users Details") {
                                        navController.navigate(Screen.UsersDetail.route)
                                    } else if (it.itemName == "Delete Menu") {
                                        navController.navigate(Screen.DeleteMenu.route)
                                    } else if (it.itemName == "Add Menu Item") {
                                        navController.navigate(Screen.AddMenu.route)
                                    } else if (it.itemName == "Create Order") {
                                        navController.navigate(Screen.AdminCreateOrder.route)
                                    } else if (it.itemName == "Order History") {
                                        navController.navigate(Screen.AdminOrderHistory.route)

                                    }
                                }
                        ) {

                            Image(
                                painter = it.painter, contentDescription = "ItemImage",
                                contentScale = ContentScale.Fit, modifier = Modifier
                                    .size(40.dp)
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(text = "  ${it.itemName}",textAlign = TextAlign.Center)
                        }
                    }

                }
            }

        }


            if(openDialog.value){
                AlertDialog(
                    onDismissRequest = { openDialog.value=false },
                    title = { Text(text = "Aleart Dialogue")},
                    text = { Text(text = "Are You Really Want to Logout", color = Color.Black, fontSize = 18.sp)},
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.sharedPreferences.edit().clear().apply()
                            navController.popBackStack()
                            navController.navigate(Screen.login.route)
                            openDialog.value=false

                        }) {
                            Text(text = "Confirm", color = Color.Black)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            openDialog.value=false
                        }) {
                            Text(text = "Dismiss", color = Color.Black)
                        }
                    },
                    backgroundColor = Color.White,
                    contentColor = Color.White
                )

            }

        }


}

data class GridModel(var itemName:String,var painter: Painter)


@Preview
@Composable
fun display(){
    // AdminData(navController = rememberNavController())
}