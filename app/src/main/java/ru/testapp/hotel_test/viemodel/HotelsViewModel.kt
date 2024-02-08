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
import ru.testapp.hotel_test.dto.Hotel
import ru.testapp.hotel_test.repository.Repository
import ru.testapp.hotel_test.statemodels.HotelState
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class HotelsViewModel @Inject constructor(
    private val repository: Repository,
    context: Context
) : ViewModel() {

    private val _hotelData: MutableStateFlow<Hotel?> = MutableStateFlow(null)
    val hotelData = _hotelData.asStateFlow()

    private val _hotelDataState = MutableLiveData<HotelState>()
    val hotelDataState: LiveData<HotelState> = _hotelDataState

    init {
        viewModelScope.launch {
            val response = repository.getHotel()
            if (response != null) {
                _hotelData.emit(response)
                _hotelDataState.value = HotelState()
            } else {
                _hotelDataState.value = HotelState(error = true)
            }
        }
    }
}