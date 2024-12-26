package com.vector.flightingapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vector.flightingapp.FlightingApplication
import com.vector.flightingapp.ui.viewmodels.HomeScreenViewModel
import com.vector.flightingapp.ui.viewmodels.TestViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightingApplication
            TestViewModel(application.container.airportRepository)
        }
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightingApplication
            HomeScreenViewModel(application.container.airportRepository)
        }
    }
}

