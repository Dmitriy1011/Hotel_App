package ru.testapp.hotel_test.repository

import kotlinx.coroutines.flow.Flow
import ru.testapp.hotel_test.dto.Hotel
import ru.testapp.hotel_test.dto.HotelRooms
import ru.testapp.hotel_test.dto.Reservation
import ru.testapp.hotel_test.dto.Tourist

interface Repository {
    val data: Flow<List<Tourist>>
    suspend fun getHotel(): Hotel
    suspend fun getHotelRooms(): HotelRooms
    suspend fun getReservationInfo(): Reservation
    suspend fun saveTourist(tourist: Tourist)
}