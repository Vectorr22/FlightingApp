package com.vector.flightingapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.vector.flightingapp.ui.components.HomeScreen

import com.vector.flightingapp.ui.theme.FlightingAppTheme
import com.vector.flightingapp.ui.util.FlightTopAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightingAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { FlightTopAppBar(
                        title = "My Flights",
                        canNavigateBack = false,
                        onNavigateBack = { /*TODO*/ })}
                ) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}




