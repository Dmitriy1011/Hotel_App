package ru.testapp.hotel_test.viemodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.testapp.hotel_test.dto.Room
import ru.testapp.hotel_test.repository.Repository
import ru.testapp.hotel_test.statemodels.HotelRoomsState
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class HotelRoomsViewModel @Inject constructor(
    private val repository: Repository,
    context: Context
) : ViewModel() {

    private val _roomsData: MutableStateFlow<List<Room>> = MutableStateFlow(listOf())
    val roomsData = _roomsData.asStateFlow()

    private val _dataState = MutableLiveData<HotelRoomsState>()
    val dataState: LiveData<HotelRoomsState>
        get() = _dataState

    init {
        viewModelScope.launch {
            try {
                val response = repository.getHotelRooms()
                if (response != null) {
                    _roomsData.emit(response.rooms)
                    _dataState.value = HotelRoomsState()
                }
            } catch (e: Exception) {
                _dataState.value = HotelRoomsState(error = true)
            }
        }
    }

    fun loadHotelRooms() {
        viewModelScope.launch {
            try {
                repository.getHotelRooms()
                _dataState.value = HotelRoomsState()
            } catch (e: Exception) {
                _dataState.value = HotelRoomsState(error = true)
            }
        }
    }
}