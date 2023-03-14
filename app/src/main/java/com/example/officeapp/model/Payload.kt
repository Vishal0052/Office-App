package com.example.officeapp.model

data class Payload(
    val accesstoken: String,
    val refreshtoken: String,
    val userInfo: UserInfo?,
    val accountStatus: Boolean?
    )