package com.example.officeapp.utils

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import androidx.navigation.NavController
import com.example.officeapp.Screen
import com.example.officeapp.model.deleteUser.DeleteUserData
import com.example.officeapp.model.userData.Payload
import com.example.officeapp.viewmodels.LoginViewModel


@Composable
fun ProjectItems(
    userdata: Payload,
    viewModel: LoginViewModel,
    selectRole: String,
    deleteUser: SnapshotStateList<Payload>
) {
    var expanded by remember { mutableStateOf(false) }
    var callDeleteUserRes by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 3.dp
    ) {
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
                    text = userdata.role,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp, modifier = Modifier
                        .weight(0.4F)
                        .padding(end = 2.dp), textAlign = TextAlign.End
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = if (userdata.isAvailable) "Available" else "Not Available",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    fontSize = 20.sp, modifier = Modifier.weight(0.6F), textAlign = TextAlign.Start
                )

                IconButton(onClick = {

//                    viewModel.deleteUser(DeleteUserData(userdata.email))
                    viewModel.deleteUser(DeleteUserData(userdata.email))
                    callDeleteUserRes=true

                    // if(Constants.deletePostStatus){
//                    deleteUser.add(userdata)
                    //  }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = "Deletion",
                        modifier = Modifier
                            .size(20.dp)
                            .weight(0.4F)
                    )
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
                                fontSize = 18.sp, modifier = Modifier.weight(0.6F)
                            )

                            Text(
                                text = if (userdata.accountStatus) "Active" else " Deactive",
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = Color.Green,
                                fontSize = 15.sp,
                                modifier = Modifier.weight(0.4F),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(25.dp)
                        .clickable {

                            expanded = !expanded
                        },
                    tint = Color.DarkGray
                )
            }
        }
    }

    if (callDeleteUserRes) {
        DeleteUserBtnResponse(loginViewModel = viewModel) {
            if (it) {
                deleteUser.add(userdata)
            }
        }
    }
}

@Composable
fun DeleteUserBtnResponse(loginViewModel: LoginViewModel, checkSuccessStatus : (Boolean) -> Unit = {}) {

    var Gcontext = LocalContext.current
    var deleteUserRes = loginViewModel.deleteUserResponse.value


    LaunchedEffect(key1 = deleteUserRes) {
        when (deleteUserRes) {
            is Resource.Success -> {

                Toast.makeText(Gcontext, "${deleteUserRes.data?.message}", Toast.LENGTH_SHORT)
                    .show()
                checkSuccessStatus.invoke(true)
            }
            is Resource.loading -> {

//                Box(
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
            }

            is Resource.Error -> {
                Toast.makeText(Gcontext, "${deleteUserRes.message}", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

}

@Composable
fun GetUsers(viewModel: LoginViewModel,navController: NavController) {

    val Context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Users Detail")},
            navigationIcon = {

                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Admin.route)

                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icon")
                }
            }, backgroundColor = Color(0xFFD1D3D5)
        )


        val roleOption = listOf("ADMIN", "SUBADMIN", "OPERATOR")
        var expanded by remember { mutableStateOf(false) }
        var selectRole by remember { mutableStateOf("") }
        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (expanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
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
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp), enabled = false,
                trailingIcon = {
                    Icon(icon, contentDescription = null, Modifier.clickable {
                        expanded = !expanded
                    })
                },
                label = { Text(text = "Select Role") }

            )

            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            )
            {
                roleOption.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectRole = label
                        expanded = false

                        viewModel.getUsers(selectRole)
                    }) {

                        Text(text = label)
                    }
                }
            }
            val getResult = viewModel.getUserResponse.value

            when (getResult) {

                is Resource.Success -> {
                    getResult.data?.let {

                        Log.e("detail", it.payload.toString())
//                        val sendRole :(String)->Unit = {
//
//                        }
                        LazyList(it.payload, viewModel, selectRole)

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
fun LazyList(payload: List<Payload>, viewModel: LoginViewModel, selectRole: String) {

    val deleteUser = remember { mutableStateListOf<Payload>() }

    LazyColumn {

        itemsIndexed(
            items = payload,
            itemContent = { index, item ->
                AnimatedVisibility(
                    visible = !deleteUser.contains(item),
                    enter = expandVertically(),
                    exit = shrinkVertically(animationSpec = tween(1000))
                ) {
                    ProjectItems(
                        userdata = item,
                        viewModel = viewModel,
                        selectRole = selectRole,
                        deleteUser=deleteUser
                    )
                }
            }
        )


    }
}

@Preview
@Composable
fun fetDisplay() {
    //GetUsers()
}