package com.vector.flightingapp.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vector.flightingapp.model.Airport
import com.vector.flightingapp.ui.AppViewModelProvider
import com.vector.flightingapp.ui.theme.FlightingAppTheme
import com.vector.flightingapp.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val homeUiState = viewModel.homeUiState.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(homeUiState.value.listOfFlights, key = {it.id}){
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(text = it.iata_code, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = it.name, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    FlightingAppTheme {
        HomeScreen()
    }
}