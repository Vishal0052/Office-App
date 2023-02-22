package com.example.officeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.officeapp.ui.theme.OfficeAppTheme
import com.example.officeapp.utils.getUsers
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
                setUpNavGraph(navController = navController,viewModel)


                //AdminData(navController)
               // OneTimeRequest()

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