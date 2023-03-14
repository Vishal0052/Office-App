package com.example.officeapp.model

data class GetMenuResponse(
    val message: String,
    val payload: List<PayloadX>,
    val status: String
)