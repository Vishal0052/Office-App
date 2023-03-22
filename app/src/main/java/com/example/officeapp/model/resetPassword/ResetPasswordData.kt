package com.example.officeapp.model.resetPassword

data class ResetPasswordData(
    val email: String,
    val password : String,
    val newPassword: String
)