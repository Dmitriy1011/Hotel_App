package ru.testapp.hotel_test.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.netology.nmedia.util.SingleLiveEvent
import ru.testapp.hotel_test.dto.Tourist
import ru.testapp.hotel_test.repository.Repository
import javax.inject.Inject

private val emptyTourist = Tourist(
    id = 0,
    title = "",
    isCardVisible = false
)

@HiltViewModel
class TouristsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val touristsData = repository.data.flowOn(Dispatchers.Default)

    private val emptyTouristsState = MutableStateFlow(emptyTourist)

    private val _addTouristError = SingleLiveEvent<String>()
    val addTouristError: SingleLiveEvent<String>
        get() = _addTouristError

    fun addTourist() {
        emptyTouristsState.value.let {
            viewModelScope.launch {
                try {
                    repository.saveTourist(it)
                } catch (e: Exception) {
                    _addTouristError.value = "Unknown error"
                }
            }
        }

        emptyTouristsState.value = emptyTourist
    }
}