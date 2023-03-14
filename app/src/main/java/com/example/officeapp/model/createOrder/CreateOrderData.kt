package com.example.officeapp.model.createOrder

data class CreateOrderData(
    val items: List<Item>,
    val orderedFrom: String,
    val orderedTo: String
)