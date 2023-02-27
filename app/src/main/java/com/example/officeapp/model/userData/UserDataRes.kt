package com.example.officeapp.model.userData

data class UserDataRes(
    val message: String,
    val payload: List<Payload>,
    val status: String
)