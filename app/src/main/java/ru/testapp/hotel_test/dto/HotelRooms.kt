package ru.testapp.hotel_test.dto

import com.google.gson.annotations.SerializedName

data class HotelRooms(
    val rooms: List<Room>
)

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    @SerializedName("price_per")
    val pricePer: String? = null,
    val peculiarities: List<String>,
    @SerializedName("image_urls")
    val imageUrls: List<String>? = null
)