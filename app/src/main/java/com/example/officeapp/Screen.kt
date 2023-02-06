package com.example.officeapp

sealed class Screen(var route :String) {
    object splash:Screen("splash_screen")
    object login:Screen("login_screen")
    object Admin:Screen("admin_screen")
    object AddSubAdmin:Screen("Add_Sub_Admin_screen")
}