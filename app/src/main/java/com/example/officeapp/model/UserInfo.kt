package com.example.officeapp.model

data class UserInfo(
    val _id: String,
    val accountStatus: Boolean?,
    val designation: String,
    val email: String,
    val isAvailable: Boolean,
    val name: String,
    val role: String
)