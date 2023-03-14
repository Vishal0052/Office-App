package com.example.officeapp.model.resetPassword

data class ResetPasswordResponse(
    val error: ResponseError?,
    val message: String,
    val status: String
)

data class ResponseError (
    val status: String,
    val message: String)