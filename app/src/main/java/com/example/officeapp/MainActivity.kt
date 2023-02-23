package com.example.officeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.officeapp.ui.theme.OfficeAppTheme
import com.example.officeapp.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

//import com.example.officeapp.utils.getUsers


@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    private val viewModel:LoginViewModel by viewModels()
    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfficeAppTheme {
                // A surface container using the 'background' color from the theme


                navController= rememberNavController()
               SetUpNavGraph(navController = navController,viewModel)


               // getUsers()
               // login(navController = navController, loginViewModel = viewModel)

            //  getUsers()




            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OfficeAppTheme {

    }
}