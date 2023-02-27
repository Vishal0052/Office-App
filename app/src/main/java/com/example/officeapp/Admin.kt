package com.example.officeapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun AdminData(navController: NavController,viewModel: LoginViewModel){

    // on below line we are creating and initializing our array list
    lateinit var courseList: List<GridModel>
    courseList = ArrayList<GridModel>()
    courseList= getItemData()

    val context = LocalContext.current

    Column() {
        TopAppBar(title = {Text(text = "Admin Panel")},
            navigationIcon = { IconButton(onClick = {
                Toast.makeText(context, "Back Icon Click", Toast.LENGTH_SHORT)
                    .show() })
            {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
            }}

        )


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
                items(courseList){

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                            .background(colorResource(id = R.color.teal_200))
                            .size(100.dp)
                            .clickable {
                                if (it.itemName == "Add Users") {
                                    navController.navigate(Screen.AddSubAdmin.route)
                                }
                                else if(it.itemName == "Users Details"){
                                    navController.navigate(Screen.UsersDetail.route)
                                }
                            }
                    ) {
                        Text(text = "Items", textAlign = TextAlign.Center)
                        Text(text = "  ${it.itemName}",textAlign = TextAlign.Center)
                    }
                }
            }

        }
    }





}

data class GridModel(var itemName:String)

fun getItemData():List<GridModel>{

    return listOf(GridModel("Add Users"),
            GridModel("Order History"),
            GridModel("menu"),
            GridModel("Users Details"),
    )
}

@Preview
@Composable
fun display(){
    // AdminData(navController = rememberNavController())
}