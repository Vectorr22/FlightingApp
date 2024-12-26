package com.vector.flightingapp.ui.viewmodels

import android.media.Image.Plane
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vector.flightingapp.data.AirportRepository
import com.vector.flightingapp.model.Airport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val airportRepository: AirportRepository
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        airportRepository.getAllAirports().map { HomeUiState(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000L),
                HomeUiState()
            )
}

data class HomeUiState(val listOfFlights: List<Airport> = listOf())

