package com.example.officeapp

import android.util.Log
import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.officeapp.ui.theme.RegisterSubAdmin
import com.example.officeapp.viewmodels.LoginViewModel

@Composable
fun setUpNavGraph(navController: NavHostController,viewModel: LoginViewModel){


    NavHost(navController = navController, startDestination = Screen.splash.route){

        composable(route = Screen.splash.route){

            splash(navController)
        }
        composable(route = Screen.login.route){
            login(navController,viewModel)

        }

        composable(route=Screen.Admin.route){
            AdminData(navController)
        }
        composable(route=Screen.AddSubAdmin.route){
            RegisterSubAdmin(navController)
        }
    }

}