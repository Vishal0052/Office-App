package com.example.officeapp

sealed class Screen(var route :String) {
    object splash:Screen("splash_screen")
    object login:Screen("login_screen")
    object Admin:Screen("admin_screen")
    object AddSubAdmin:Screen("Add_Sub_Admin_screen")
    object UsersDetail:Screen("Users_Detail")
    object DeleteMenu : Screen("Delete_Menu_Item")
    object AddMenu : Screen("Add_Menu")
    object AdminCreateOrder : Screen("Admin_Create_Order_Screen")
    object AdminOrderHistory : Screen("Admin_Order_History_Screen")

    object SubAdmin : Screen("SubAdmin_Screen")

    object SAdminCreateOrder : Screen("SAdmin_Create_Order")

    object SAdminOrderHistory : Screen("SAdmin_Order_History")

    object  OneTimeRequest :Screen("One_Time_Request_Screen")

    object pendingOrderScreen : Screen("pending_Order_Screen")

    object operatorDashboardScreen : Screen("operator_Dashboard_Screen")

    object CompletedOrderScreen : Screen("Completed_Order_Screen")

    object ForgetPassword : Screen("Forget_Password")

}