package ru.testapp.hotel_test.repository

import android.accounts.NetworkErrorException
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.testapp.hotel_test.api.HotelApiService
import ru.testapp.hotel_test.dto.Hotel
import ru.testapp.hotel_test.dto.HotelRooms
import ru.testapp.hotel_test.dto.Reservation
import ru.testapp.hotel_test.dto.Tourist
import ru.testapp.hotel_test.exceptions.ApiError
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val hotelApiService: HotelApiService,
    private val context: Context
) : Repository {

    var touristIdCounter = 1;

    private var tourists = listOf(
        Tourist(
            id = 1,
            title = "Первый турист",
            isCardVisible = true
        )
    )

    private val _data = MutableStateFlow(tourists)
    override val data = _data.asStateFlow()

    override suspend fun saveTourist(tourist: Tourist) {
        if (tourists.size >= 5) {
            return
        }
        tourists = listOf(
            tourist.copy(
                id = ++touristIdCounter,
                title = when (touristIdCounter) {
                    2 -> "Второй турист"
                    3 -> "Третий турист"
                    4 -> "Четвертый турист"
                    5 -> "Пятый турист"
                    else -> ""
                },
                isCardVisible = false
            )
        ) + tourists
        _data.update { tourists.reversed() }
        return
    }

    override suspend fun getHotel(): Hotel {
        try {
            val response = hotelApiService.getHotel()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun getHotelRooms(): HotelRooms {
        try {
            val response = hotelApiService.getHotelRooms()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())

        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun getReservationInfo(): Reservation {
        try {
            val response = hotelApiService.getInfoForReservation()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }
}