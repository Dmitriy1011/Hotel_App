package ru.testapp.hotel_test.api

import retrofit2.Response
import retrofit2.http.GET
import ru.testapp.hotel_test.dto.Hotel
import ru.testapp.hotel_test.dto.HotelRooms
import ru.testapp.hotel_test.dto.Reservation


interface HotelApiService {
    @GET("v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(): Response<Hotel>

    @GET("v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getInfoForReservation(): Response<Reservation>

    @GET("v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getHotelRooms(): Response<HotelRooms>
}
