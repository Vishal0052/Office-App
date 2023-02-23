package com.example.officeapp.utils

import android.service.autofill.UserData
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.officeapp.model.userData


fun getUsersList(): List<userData> {
    return listOf(
        userData(name = "vishal", designation = "Software Developer Intern "),
        userData(name = "Media Player App", designation = "Listen Music Endlesly"),
        userData(name = "Gaming Media App", designation = "Playing with Friend with your Friend"),
        userData(name = "Social Media App", designation = "Connect with your Friend"),
        userData(name = "Media Player App", designation = "Listen Music Endlesly"),
        userData(name = "Gaming Media App", designation = "Playing with Friend with your Friend")

    )

}

@Composable
fun ProjectItems(
    userdata: userData,
    passinguserdata: (userData) -> Unit,
    onDeleteItem: () -> Unit
) {


//    Row(modifier= Modifier
//        .fillMaxWidth()
//        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
//        , horizontalArrangement = Arrangement.SpaceBetween) {
//        Column(modifier= Modifier.padding(end = 10.dp)) {
//            Text(text = userdata.name, style = MaterialTheme.typography.h6)
//            Text(text = userdata.designation, style = MaterialTheme.typography.body1)
//        }
//        IconButton(onClick = {
//            passinguserdata(userdata)
//            onDeleteItem()
//        }) {
//            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Deletion")
//        }
//
//
//    }

    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
    )
    {
        Box(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp),
                shape = RectangleShape,
                elevation = 4.dp
            ) {

                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Movie Image")
            }

            Column(modifier = Modifier
                .padding(start = 24.dp)
                .align(Alignment.Center)) {
                Text(text = userdata.name)
                Text(text = userdata.designation)
                Text(text = userdata.name)

                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {

                            expanded = !expanded
                        },
                    tint = Color.DarkGray
                )

                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text(text = "djhddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")

                        Divider(modifier = Modifier.padding(3.dp))

                        Text(text = userdata.name)
                    }
                }
            }

            IconButton(onClick = {

            }, modifier = Modifier
                .align(Alignment.CenterEnd)) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Deletion")
            }
        }
    }


}

@Composable
fun getUsers() {

    val deletedItem = remember { mutableStateListOf<userData>() }



    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Users List") },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ })
                {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                }
            })

        LazyColumn {

            itemsIndexed(
                items = getUsersList(),
                itemContent = { index, item ->

                    AnimatedVisibility(
                        visible = !deletedItem.contains(item),
                        enter = expandVertically(),
                        exit = shrinkVertically(animationSpec = tween(1000))
                    ) {
                        ProjectItems(item, { passinguserdata ->
                            deletedItem.add(passinguserdata)
                        }, {

                        })

                    }
                }
            )
        }

    }

}

@Preview
@Composable
fun fetDisplay() {
    getUsers()
}