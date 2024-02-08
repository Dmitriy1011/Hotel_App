package ru.testapp.hotel_test.dto

data class Tourist(
    val id: Int,
    val title: String,
    var isCardVisible: Boolean = false
)