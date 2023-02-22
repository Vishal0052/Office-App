package com.example.officeapp.utils

import android.service.autofill.UserData
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.officeapp.model.userData


fun getUsersList():List<userData>{
    return listOf(
        userData(name = "Social", designation = "Connect with ") ,
        userData(name = "Media Player App", designation = "Listen Music Endlesly"),
        userData(name = "Gaming Media App", designation = "Playing with Friend with your Friend"),
        userData(name = "Social Media App", designation = "Connect with your Friend") ,
        userData(name = "Media Player App", designation = "Listen Music Endlesly"),
        userData(name = "Gaming Media App", designation = "Playing with Friend with your Friend")

    )

}

@Composable
fun ProjectItems(userdata : userData,passinguserdata:(userData)->Unit,onDeleteItem:()->Unit) {


    Row(modifier= Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
        , horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier= Modifier.padding(end = 10.dp)) {
            Text(text = userdata.name, style = MaterialTheme.typography.h6)
            Text(text = userdata.designation, style = MaterialTheme.typography.body1)
        }
        IconButton(onClick = {
            passinguserdata(userdata)
            onDeleteItem()
        }) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Deletion")
        }


    }

}
@Composable
fun getUsers(){

    val deletedItem =remember{ mutableStateListOf<userData>() }

Column(modifier = Modifier.fillMaxSize()) {
    TopAppBar(title = { Text(text = "Users List")},
        navigationIcon = { IconButton(onClick = { /*TODO*/ })
        {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
        }})
    LazyColumn{

        itemsIndexed(
            items = getUsersList(),
            itemContent = {index, item ->

                AnimatedVisibility(visible = !deletedItem.contains(item),
                    enter = expandVertically(),
                    exit = shrinkVertically(animationSpec = tween(1000))
                ) {
                    ProjectItems(item,{passinguserdata->
                        deletedItem.add(passinguserdata)
                    },{

                    })

                }

//                ProjectItems(item)
//                deletedItem.remove(item)

            }
        )
//        items(getUsersList()){
//            ProjectItems(it)
//
//        }
    }

}







}
@Preview
@Composable
fun fetDisplay(){
    getUsers()
}