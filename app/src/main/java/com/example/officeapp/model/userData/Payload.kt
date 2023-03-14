package com.example.officeapp.model.userData

data class Payload(
    val _id : String,
    val accountStatus: Boolean,
    val designation: String,
    val email: String,
    val isAvailable: Boolean,
    val name: String,
    val password: String,
    val role: String
)