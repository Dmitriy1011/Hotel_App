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
import ru.testapp.hotel_test.dto.Reservation
import ru.testapp.hotel_test.repository.Repository
import ru.testapp.hotel_test.statemodels.ReservationState
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class ReservationViewModel @Inject constructor(
    private val repository: Repository,
    context: Context
) : ViewModel() {

    private val _dataReservation: MutableStateFlow<Reservation?> = MutableStateFlow(null)
    val dataReservation = _dataReservation.asStateFlow()

    private val _dataState = MutableLiveData<ReservationState>()
    val dataState: LiveData<ReservationState>
        get() = _dataState

    init {
        viewModelScope.launch {
            val response = repository.getReservationInfo()
            if (response != null) {
                _dataReservation.emit(response)
            } else {
                _dataState.value = ReservationState(error = true)
            }
        }
    }
}