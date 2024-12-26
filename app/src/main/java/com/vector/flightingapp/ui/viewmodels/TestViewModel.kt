package com.vector.flightingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vector.flightingapp.data.AirportRepository
import com.vector.flightingapp.model.Airport
import kotlinx.coroutines.launch

class TestViewModel(
    private val aiportRepository: AirportRepository
):ViewModel() {
    fun test1(){
        viewModelScope.launch {
            aiportRepository.insertItem(Airport(
                name = "Test1",
                iata_code = "VIC",
                passengers = 100
            ))
        }
    }
}

