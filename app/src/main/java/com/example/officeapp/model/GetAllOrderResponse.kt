package com.example.officeapp.model

data class GetAllOrderResponse(
    val message: String,
    val payload: List<PayloadXX>,
    val status: String
)