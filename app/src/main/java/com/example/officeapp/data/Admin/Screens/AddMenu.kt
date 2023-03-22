package com.example.officeapp.data

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentResolverCompat
import androidx.navigation.NavController
import com.example.officeapp.R
import com.example.officeapp.Screen
import com.example.officeapp.common.CommonTf
import com.example.officeapp.data.Admin.Screens.LoadingBar
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.model.addMenu.AddMenuData
import com.example.officeapp.utils.Resource
import com.example.officeapp.utils.Utils
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun AddMenu(loginViewModel: LoginViewModel,navController: NavController) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    var checkAddClickRes by remember { mutableStateOf(false) }
    var menuItemName by remember { mutableStateOf("") }

    var launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            Log.d("vishal", "AddMenu: ${it.toString()}")
            imageUri = it


//        if (it != null)
//        {
//        /*  var getPath =  it.path
//            Log.e("check",getPath.toString())*/
//
//        }
        }

    Column() {
        TopAppBar(
            title = { Text(text = "Add Menu") },
            navigationIcon = {

                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Admin.route)

                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icon")
                }
            }, backgroundColor = Color(0xFFD1D3D5)
        )



        Surface {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                imageUri?.let {
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }
                }


                bitmap?.asImageBitmap()
                    ?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "",
                            modifier = Modifier.size(200.dp)
                        )
                    }


                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = { launcher.launch("image/*") })
                {
                    Text(text = "Pick Image")
                }

                Spacer(modifier = Modifier.height(5.dp))



                CommonTf(text = menuItemName, label = "Enter Menu Item Name", onTextChange = {
                    menuItemName = it
                })


                Button(
                    onClick = {
                        if (menuItemName.isBlank()) {
                            Toast.makeText(context, "Menu Name is Empty", Toast.LENGTH_SHORT).show()
                        } else {
                            loginViewModel.addMenuItem(AddMenuData(menuItemName))
                            checkAddClickRes = true
                        }


                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 15.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(text = "ADD", textAlign = TextAlign.Center)
                }
            }

        }


        if (checkAddClickRes) {
            var addMenuRes = loginViewModel.addMenuResponse.value
            var isLoadingAddMenu by remember { mutableStateOf(false) }
            if (isLoadingAddMenu) LoadingBar()

            LaunchedEffect(key1 = addMenuRes) {
                when (addMenuRes) {

                    is Resource.Success -> {
                        isLoadingAddMenu = false
                        Toast.makeText(context, "${addMenuRes.data?.message}", Toast.LENGTH_SHORT)
                            .show()
                        navController.popBackStack()
                        navController.navigate(Screen.Admin.route)
                    }
                    is Resource.Error -> {
                        isLoadingAddMenu = false
                        Toast.makeText(context, "${addMenuRes.message}", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.loading -> {
                        isLoadingAddMenu = true
                    }
                    else -> {}
                }
            }
        }

    }
}