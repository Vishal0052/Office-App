package com.example.officeapp

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.officeapp.data.Admin.Screens.LoadingBar
import com.example.officeapp.model.resetPassword.ResetPasswordData
import com.example.officeapp.utils.Resource
import com.example.officeapp.utils.Utils
import com.example.officeapp.utils.showError
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun OneTimeRequest(navController: NavHostController, viewModel: LoginViewModel) {

    var isLoading by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.Center
    , horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var isErroremail by remember { mutableStateOf(false)}
        var isErrorpassword by remember {
            mutableStateOf(false)

        }

        var isErrornewpassword by remember { mutableStateOf(false) }
        val context = LocalContext.current


        Text(text = "Update Password", fontSize = 30.sp
        , fontWeight = FontWeight.Bold
                    ,color = Color.Blue,
            textAlign = TextAlign.Left, modifier = Modifier.padding(top = 10.dp)
            )
        Text(text = "One Time Password Change", fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        textAlign = TextAlign.Center, modifier = Modifier.padding(top = 10.dp)
       )

        OutlinedTextField(value = email, onValueChange = {
            email=it
            isErroremail = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        },
        leadingIcon = {Icon(Icons.Default.Email, contentDescription = "null")}
        ,isError = isErroremail,
            label = { Text(text = "Enter Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp))
        if(isErroremail){

         showError(errorText = "Email is not valid", errorcheck = isErroremail)
        }

        OutlinedTextField(
            value = password, onValueChange = {
                password = it
                isErrorpassword = it.length <= 3
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "person")
            },
            label = { Text(text = "Enter Password") }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp),
            isError = isErrorpassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

        )

        if (isErrorpassword) {
            showError(errorText = "password Length is Small", errorcheck = isErrorpassword)
        }

        OutlinedTextField(
            value = newPassword, onValueChange = {
                newPassword = it
                isErrornewpassword = it.length <= 3
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "password")
            },
            label = { Text(text = "Enter New Password") }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp),
               isError = isErrornewpassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (isErrornewpassword) {
            showError(errorText = "password Length is Small", errorcheck = isErrornewpassword)
        }

        Button(
            onClick =
            {

                if (Utils.checkEmail(email, context) && Utils.checkPassword(password, context)) {
                    //Toast.makeText(context, "Login Sucess", Toast.LENGTH_SHORT).show()
                    viewModel.resetPass(ResetPasswordData(email,password,newPassword))
                }


            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp, top = 15.dp),
            shape = RoundedCornerShape(50.dp)
        ) {

            Text(text = "Login", textAlign = TextAlign.Center)
        }

    }
    var context = LocalContext.current
    if(isLoading) LoadingBar()
    val oneTimeRequestResult = viewModel.resetResponse.value

    LaunchedEffect(key1 = oneTimeRequestResult) {

        when (oneTimeRequestResult) {
            is Resource.Success -> {
                isLoading=false
                oneTimeRequestResult.data?.let {
                    Toast.makeText(context, "Password Reset Succesfully", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    navController.navigate(Screen.login.route)


                }
            }
            is Resource.loading -> {

                isLoading=true

            }
            is Resource.Error -> {
                Toast.makeText(context, "failed ${oneTimeRequestResult.message}", Toast.LENGTH_SHORT).show()
                isLoading=false
            }
            else -> {}
        }
    }
}
@Preview
@Composable
fun show(){
//    OneTimeRequest(navController)
}