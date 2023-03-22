package com.example.officeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.utils.Constants
import com.example.officeapp.viewmodels.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun splash(navController: NavController,viewModel:LoginViewModel) {


    val email = viewModel.getValueFromPref(Constants.USER_EMAIL)
    val password = viewModel.getValueFromPref(Constants.USER_PASSWORD)

    if(email!=null && password!=null){

        LaunchedEffect(key1 = true){
            viewModel.loginUser(LoginDataModel(email,password))
        }

    }
    else{
        LaunchedEffect(key1 = true){
            delay(3000)
            navController.popBackStack()
            navController.navigate(Screen.login.route)
        }
    }




    Column(modifier = Modifier.fillMaxSize().background(Color("#ffffff".toColorInt())),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.antino_logo), contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp))

        Text(text = "Office App",
                color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold )
    }

}

@Composable
@Preview
fun splashPreview(){
   // splash(navController = rememberNavController())
}