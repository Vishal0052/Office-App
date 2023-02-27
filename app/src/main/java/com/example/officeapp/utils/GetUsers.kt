package com.example.officeapp.utils

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.officeapp.model.UserData
import com.example.officeapp.model.userData.Payload
import com.example.officeapp.viewmodels.LoginViewModel



@Composable
fun ProjectItems(
    userdata : Payload,

    ) {


    var expanded by remember{ mutableStateOf(false) }
    
    Card( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 3.dp) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {



                Text(
                    text = userdata.name,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp, modifier = Modifier.weight(0.6F)
                )

                Text(
                    text = userdata.designation,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,modifier = Modifier.weight(0.4F)
                )



            }

            Row(horizontalArrangement = Arrangement.Center
                  , verticalAlignment = Alignment.CenterVertically ){

                Text(
                    text = "Available",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    fontSize = 20.sp ,modifier = Modifier.weight(0.6F), textAlign = TextAlign.Start
                )

                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Deletion",
                        modifier = Modifier
                            .size(20.dp)
                            .weight(0.4F)            )
                }


            }


            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = expanded) {


                    Column {

                        Text(
                            text = userdata.email,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp, fontStyle = FontStyle.Italic
                        )

                        Row() {
                            Text(
                                text = userdata.designation,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                                fontSize = 18.sp, modifier = Modifier.weight(0.6F))

                            Text(
                                text = userdata.accountStatus.toString(),
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = Color.Green,
                                fontSize = 15.sp, modifier = Modifier.weight(0.4F), textAlign = TextAlign.End
                            )

                        }

                    }

                }

                Icon(imageVector = if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = "Down Arrow",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(25.dp)
                        .clickable {

                            expanded = !expanded
                        }, tint = Color.DarkGray
                )

            }



            }


        }




}

@Composable
fun GetUsers(viewModel: LoginViewModel) {

    val deletedItem = remember { mutableStateListOf<UserData>() }

    val Context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Users List") },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ })
                {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                }
            })


        val roleOption = listOf("ADMIN","SUBADMIN","OPERATOR")
        var expanded by remember { mutableStateOf(false) }
        var selectRole by remember { mutableStateOf("") }
        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        val icon =if (expanded){
            Icons.Filled.KeyboardArrowUp
        }else{
            Icons.Filled.KeyboardArrowDown
        }

        Column {

            OutlinedTextField(
                value = selectRole, onValueChange = {
                    selectRole = it

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {

                        // This value is used to assign to
                        // the DropDown the same width

                        textFieldSize = it.size.toSize()
                    }
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp),enabled = false,
                trailingIcon = {
                    Icon(icon , contentDescription = null, Modifier.clickable {
                        expanded=!expanded
                    })
                },
                label = { Text(text = "Select Role") }

            )

            DropdownMenu(expanded = expanded, onDismissRequest = {expanded=false},
                modifier = Modifier
                    .width(with(LocalDensity.current){textFieldSize.width.toDp()})
            )
            {
                roleOption.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectRole=label
                        expanded=false
                        viewModel.getUsers(selectRole)
                    }) {

                        Text(text = label)
                    }
                }
            }
            val getResult = viewModel.getUserResponse.value
            when(getResult){

                is Resource.Success -> {
                    getResult.data?.let {

                        Log.e("detail", it.payload.toString())
                        LazyList(it.payload)

                    }
                }

                is Resource.Error -> {
                    Toast.makeText(Context, "${getResult.message}", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }

        }


    }

}

@Composable
fun LazyList(payload: List<Payload>) {
    LazyColumn {

        itemsIndexed(
            items = payload,
            itemContent = { index, item ->


                    ProjectItems(item)


            }
        )
    }
}

@Preview
@Composable
fun fetDisplay() {
    //GetUsers()
}