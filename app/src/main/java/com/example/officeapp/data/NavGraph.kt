package com.example.officeapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.officeapp.data.AddMenu
import com.example.officeapp.data.Admin.Screens.AdminCreateOrderScreen
import com.example.officeapp.data.Admin.Screens.AdminOrderHistory
import com.example.officeapp.data.Admin.Screens.DeleteMenuItem
import com.example.officeapp.data.ForgetPassword
import com.example.officeapp.data.LoginScreen
import com.example.officeapp.data.SubAdmin.SAdminCreateOrder
import com.example.officeapp.data.SubAdmin.SAdminOrderHistory
import com.example.officeapp.data.SubAdmin.SubAdmin
import com.example.officeapp.data.operator.CompletedOrder
import com.example.officeapp.data.operator.OperatorDashboard
import com.example.officeapp.data.operator.OperatorViewModel
import com.example.officeapp.data.operator.PendingOrderScreen
import com.example.officeapp.ui.theme.RegisterSubAdmin
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Constants.deletePostStatus
import com.example.officeapp.utils.GetUsers
import com.example.officeapp.utils.Resource
import com.example.officeapp.viewmodels.LoginViewModel
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: LoginViewModel,
    operatorViewModel: OperatorViewModel
){
    val mContext = LocalContext.current
    val deleterResult = viewModel.deleteUserResponse.value
    val createResult = viewModel.createUserResponse.value


    when(createResult){
        is Resource.Success ->{
            Toast.makeText(mContext,createResult.data?.message,Toast.LENGTH_LONG).show()
            navController.popBackStack()
            navController.navigate(Screen.Admin.route)
        }

        is Resource.Error -> {
            Toast.makeText(mContext, "${createResult.message}", Toast.LENGTH_SHORT).show()
        }
        is Resource.loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {}
    }


    when(deleterResult){

        is Resource.Success -> {
            Toast.makeText(mContext, deleterResult.data?.message, Toast.LENGTH_SHORT).show()
            deletePostStatus=true
        }

        is Resource.Error -> {
            Toast.makeText(mContext, "${deleterResult.message}", Toast.LENGTH_SHORT).show()
            deletePostStatus=false

        }

        else -> {}
    }

    when(val loginResponseResult = viewModel.loginResponse.value) {
        is Resource.Success -> {
            LaunchedEffect(key1 = Unit) {
                loginResponseResult.data?.payload?.let { // here we check our resut paylod is not null tahn we move forward
                    viewModel.saveValueInPref(Constants.AUTH_TOKEN, it.accesstoken)
                    it.userInfo?.let { it1 ->
                        viewModel.saveValueInPref(Constants.USER_NAME,
                            it1.name)
                    }
                    try {
                        if(it.userInfo?.accountStatus == true) {
                            viewModel.saveValueInPref(Constants.USER_ID, it.userInfo._id)
                        }
                        if(it.accountStatus == false) {
                            Log.e("reached", "Result")
                            navController.popBackStack()
                            navController.navigate(Screen.OneTimeRequest.route)

                        }
                    }catch (e :Exception){
                        Log.e("err",e.toString())
                    }
                    viewModel.saveUserDetails()
                }

                 if (loginResponseResult.data?.payload?.userInfo?.role == "ADMIN") {
                     navController.popBackStack()
                    navController.navigate(Screen.Admin.route)
                    Log.e("login:1", "Result")

                }
                else if(loginResponseResult.data?.payload?.userInfo?.role == "OPERATOR"){
                     navController.popBackStack()
                    navController.navigate(Screen.operatorDashboardScreen.route)
                }
                else if(loginResponseResult.data?.payload?.userInfo?.role == "SUBADMIN"){
                     navController.popBackStack()
                    navController.navigate(Screen.SubAdmin.route)
                }

            }


        }
        is Resource.Error -> {
            Log.e("login:2","Result")
            Toast.makeText(mContext, "${loginResponseResult.message}", Toast.LENGTH_SHORT).show()
        }
        is Resource.loading -> {

        }
        else ->{
            Log.e("login:4","Result")

        }
    }

    NavHost(navController = navController, startDestination = Screen.splash.route){

        composable(route = Screen.splash.route){

            splash(navController,viewModel)
        }
        composable(route = Screen.login.route){

            LoginScreen(viewModel,navController)
        }

        composable(route=Screen.Admin.route){
            AdminData(navController,viewModel)
        }
        composable(route=Screen.AddSubAdmin.route){
            RegisterSubAdmin(navController,viewModel)
        }
        composable(route=Screen.UsersDetail.route){
            GetUsers(viewModel,navController)
        }

        composable(route = Screen.DeleteMenu.route){
            DeleteMenuItem(viewModel,navController)
        }

        composable(route=Screen.AddMenu.route){
            AddMenu(viewModel,navController)
        }
        composable(route=Screen.AdminCreateOrder.route){
            AdminCreateOrderScreen(navController,viewModel)
        }
        composable(route=Screen.AdminOrderHistory.route){
            AdminOrderHistory(viewModel,navController)
        }

        composable(route = Screen.SubAdmin.route){
            SubAdmin(navController = navController,viewModel)
        }

        composable(route=Screen.OneTimeRequest.route){
            OneTimeRequest(navController=navController,viewModel)
        }

        composable(route = Screen.SAdminCreateOrder.route){
            SAdminCreateOrder(navController,viewModel)
        }

        composable(route = Screen.SAdminOrderHistory.route){
            SAdminOrderHistory(viewModel,navController)
        }

        composable(route = Screen.pendingOrderScreen.route){
            PendingOrderScreen(operatorViewModel = operatorViewModel,navController)
        }

        composable(route = Screen.operatorDashboardScreen.route){
            OperatorDashboard(navController,operatorViewModel)
        }

        composable(route = Screen.CompletedOrderScreen.route){
            CompletedOrder(operatorViewModel)
        }

        composable(route = Screen.ForgetPassword.route){
            ForgetPassword(loginViewModel = viewModel, navController = navController)
        }
    }

}

//fun setLoginResponse(context: Context, viewModel: LoginViewModel, navController: NavController){
//    Log.e("login:0","Result")
//
//
//    when(val loginResponseResult = viewModel.loginResponse.value) {
//        is Resource.Success -> {
//            loginResponseResult.data?.payload?.let { // here we check our resut paylod is not null tahn we move forward
//                viewModel.saveValueInPref(Constants.AUTH_TOKEN,it.accesstoken)
//                viewModel.saveUserDetails()
//            }
//            if (loginResponseResult.data?.payload?.userInfo?.role == "ADMIN") {
//                navController.navigate(Screen.Admin.route)
//                Log.e("login:1","Result")
//
//            }
//            if(loginResponseResult.data?.payload?.userInfo?.accountStatus == true){
//                navController.navigate(Screen.OneTimeRequest.route)
//
//            }
//        }
//        is Resource.Error -> {
//            Log.e("login:2","Result")
//            Toast.makeText(context, "${loginResponseResult.message}", Toast.LENGTH_SHORT).show()
//        }
//        is Resource.loading -> {
//            Log.e("login:3","Result")
//
//            //  CircularProgressIndicator()
//        }
//        else ->{
//            Log.e("login:4","Result")
//
//        }
//    }
//}