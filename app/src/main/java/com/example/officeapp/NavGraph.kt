package com.example.officeapp

import android.util.Log
import android.widget.Toast
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.officeapp.ui.theme.RegisterSubAdmin
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun SetUpNavGraph(navController: NavHostController, viewModel: LoginViewModel){
    val mContext = LocalContext.current

    when(val result = viewModel.loginResponse.value) {
        is Resource.Success -> {
            result.data?.payload?.let {
                viewModel.saveValueInPref(Constants.AUTH_TOKEN,it.accesstoken)
                viewModel.saveUserDetails()
            }
            if (result.data?.payload?.userInfo?.role == "ADMIN") {
                navController.navigate(Screen.AddSubAdmin.route)
            }
        }
        is Resource.Error -> {
            Toast.makeText(mContext, "${result.message}", Toast.LENGTH_SHORT).show()
        }
        is Resource.loading -> {
          //  CircularProgressIndicator()
        }
        else ->{}
    }

    when(val createResult = viewModel.createUserResponse.value){
        is Resource.Success ->{
            Toast.makeText(mContext,createResult.data?.message,Toast.LENGTH_LONG).show()
            navController.navigate(Screen.Admin.route)
        }

        is Resource.Error -> {
            Toast.makeText(mContext, "${createResult.message}", Toast.LENGTH_SHORT).show()
        }
        is Resource.loading -> {
            Toast.makeText(mContext, "Loading", Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }








    NavHost(navController = navController, startDestination = Screen.splash.route){

        composable(route = Screen.splash.route){

            splash(navController,viewModel)
        }
        composable(route = Screen.login.route){
            login(viewModel)
        }

        composable(route=Screen.Admin.route){
            AdminData(navController)
        }
        composable(route=Screen.AddSubAdmin.route){
            RegisterSubAdmin(navController,viewModel)
        }
    }

}