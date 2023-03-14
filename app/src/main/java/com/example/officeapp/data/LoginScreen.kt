package com.example.officeapp.data

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.officeapp.Screen
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Resource
import com.example.officeapp.utils.Utils.checkEmail
import com.example.officeapp.utils.Utils.checkPassword
import com.example.officeapp.utils.showError
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {

    val mContext = LocalContext.current

    val Teal200 = "#20d9f8"
    var isErroremail by remember {
        mutableStateOf(false)
    }
    var isErrorpassword by remember {
        mutableStateOf(false)
    }

//    LaunchedEffect(key1 = false) {
//        setLoginResponse(mContext, loginViewModel, navController)
//    }

  //  setLoginResponse(mContext,loginViewModel, navController)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(Teal200.toColorInt()))
    ) {

        var email by remember { mutableStateOf("") }
        var passWord by remember { mutableStateOf("") }

        Text(
            text = "Login Here", fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            textAlign = TextAlign.Center,

            )


        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isErroremail = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },

            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "person")
            },

            isError = isErroremail,

            label = { Text(text = "Enter Your Mail") }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp)

        )
        if (isErroremail) {
            showError(errorText = "Entered Email is Not Valid", errorcheck = isErroremail)
        }

        OutlinedTextField(
            value = passWord, onValueChange = {
                passWord = it
                isErrorpassword = it.length <= 3
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "person")
            },
            label = { Text(text = "Enter Password") }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = isErrorpassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

        )

        if (isErrorpassword) {
            showError(errorText = "password Length is Small", errorcheck = isErrorpassword)
        }

        Button(onClick = {
                if (checkEmail(email, mContext) && checkPassword(passWord, mContext)) {

                    loginViewModel.loginUser(LoginDataModel(email,passWord))

                }
            }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp, top = 15.dp),
            shape = RoundedCornerShape(50.dp)
        ) {

            Text(text = "Login", textAlign = TextAlign.Center)
        }
    }



}

