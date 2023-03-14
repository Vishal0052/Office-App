package com.example.officeapp.model.resetPassword

data class ResetPasswordData(
    val email: String,
    val newPassword: String,
    val password: String
)