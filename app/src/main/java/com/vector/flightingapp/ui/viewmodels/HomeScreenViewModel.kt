package com.vector.flightingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vector.flightingapp.data.AirportRepository
import com.vector.flightingapp.data.FavoriteRepository
import com.vector.flightingapp.data.datastore.HistoryRepository
import com.vector.flightingapp.model.Airport
import com.vector.flightingapp.model.Favorite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeUiState())

    val homeUiState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    init {
        viewModelScope.launch {
            _homeState.update { current ->
                current.copy(
                    listOfFavorites = favoriteRepository.getAllFavorites()
                )
            }
        }
    }

    fun updateCurrentSearch(search: String) {
        viewModelScope.launch {
            _homeState.update { current ->
                if (search.isEmpty()) {
                    current.copy(
                        listOfCurrentSearch = emptyList()
                    )
                } else {
                    current.copy(
                        listOfCurrentSearch = airportRepository.updateSearchList(search)
                    )
                }
            }
        }
    }

    fun getFilteredAirportList(iata_code: String) {
        viewModelScope.launch {
            _homeState.update { current ->
                current.copy(
                    listOfSearched = airportRepository.getExcludedListOfAirports(iata_code)
                )
            }
        }
    }

    fun clearFilteredAirportList() {
        _homeState.update { current ->
            current.copy(
                listOfSearched = emptyList()
            )
        }
    }

    fun saveFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val favorite = Favorite(
                departure_code = departureCode,
                destination_code = destinationCode
            )
            favoriteRepository.insertFavorite(favorite)
            _homeState.update { current ->
                current.copy(
                    listOfFavorites = favoriteRepository.getAllFavorites()
                )
            }
        }
    }

    fun confirmFlight() {
        viewModelScope.launch {
            _homeState.update { current ->
                current.copy(
                    isLoading = true
                )
            }
            delay(5000L)
            _homeState.update { current ->
                current.copy(
                    isLoading = false
                )
            }
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(favorite)
        }
    }

    fun getAllHistorySearch() {
        viewModelScope.launch {
            val codedList = historyRepository.getHistory().first()
            val uncodedList = if (codedList.isEmpty()) emptyList() else codedList.split(",")
            _homeState.update { current ->
                current.copy(
                    listOfHistory = uncodedList
                )
            }

        }
    }

    fun saveNewHistory(value: String) {
        viewModelScope.launch {
            val currentHistory = historyRepository.getHistory().first()
            if(currentHistory.isEmpty())
                println("esta vacio")
            else
                println(currentHistory)
            val newHistory =
                if (currentHistory.isNotEmpty()) currentHistory.plus(",$value") else value

            historyRepository.saveHistory(newHistory)
        }
    }
}


data class HomeUiState(
    val listOfCurrentSearch: List<Airport> = emptyList(),
    val listOfFavorites: List<Favorite> = emptyList(),
    val listOfSearched: List<Airport> = emptyList(),
    var isLoading: Boolean = false,
    val listOfHistory: List<String> = emptyList()
)

