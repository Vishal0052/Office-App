package com.example.officeapp.model

data class PayloadXX(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val items: List<Item>,
    val orderStatus: String,
    val orderedBy: String,
    val orderedFrom: String,
    val orderedTo: String,
    val updatedAt: String
)