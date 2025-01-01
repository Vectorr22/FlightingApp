package com.vector.flightingapp.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vector.flightingapp.ui.AppViewModelProvider
import com.vector.flightingapp.ui.theme.FlightingAppTheme
import com.vector.flightingapp.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val homeUiState = viewModel.homeUiState.collectAsState()
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }
    var showDialog by remember { mutableStateOf(false) }
    val mutableInteraction = remember { MutableInteractionSource() }
    var selected by remember { mutableStateOf(false) }
    var isSavedRoute by remember { mutableStateOf(false) }
    var departureCode by remember {
        mutableStateOf("")
    }
    var destinationCode by remember{ mutableStateOf("") }
    Box(modifier = modifier.fillMaxSize()) {
        if (showDialog) {
            ConfirmDialog(
                title = "Please confirm your flight",
                departureCode = departureCode,
                destinationCode = destinationCode,
                onDismissDialog = { showDialog = false },
                onConfirmedButtonPressed = {
                    viewModel.confirmFlight()
                    textFieldValue = textFieldValue.copy("")
                    selected = false
                    showDialog = false
                    viewModel.clearFilteredAirportList()
                },
                onFavoriteAction = {
                    viewModel.saveFavorite(departureCode,destinationCode)
                }
            )
        }
        Column {
            Column(
                modifier = Modifier
                    .weight(0.1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                TextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        expanded = true
//                        expanded = textFieldValue.text.isNotEmpty()
                        viewModel.updateCurrentSearch(textFieldValue.text)
                        if(it.text.isEmpty()){
                            viewModel.getAllHistorySearch()
                            Log.i("Victor",homeUiState.value.listOfHistory.size.toString())
                        }
                    },
                    enabled = !selected,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                textFieldValue = textFieldValue.copy("")
                                expanded = false
                            },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = if (selected) Color.Gray else Color.Black),
                            enabled = !selected
                        ) {
                            Icon(imageVector = Icons.Rounded.Clear, contentDescription = "null")
                        }
                    },
                    placeholder = { Text(text = "Enter your start destination") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    interactionSource = mutableInteraction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .onGloballyPositioned { size ->
                            textFieldSize = size.size.toSize()
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnimatedVisibility(visible = selected) {
                        ElevatedButton(
                            onClick = {
                                selected = false
                                departureCode = ""
                                textFieldValue = textFieldValue.copy("")
                                viewModel.clearFilteredAirportList()
                            },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Color(0xFFEC3737)
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }
                    }
                    ElevatedButton(
                        onClick = {
                            if(textFieldValue.text.isNotEmpty()){
                                departureCode = obtainIataCode(textFieldValue.text)
                                viewModel.getFilteredAirportList(iata_code = departureCode)

                                viewModel.saveNewHistory(textFieldValue.text)
                                selected = true
                            }
                        },
                        enabled = !selected,
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF2196F3)
                        ),
                        modifier = Modifier.width(150.dp)
                    ) {
                        Text(
                            text = "Select",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                if (homeUiState.value.listOfSearched.isNotEmpty()) {
                    Text(
                        text = "Select a destination",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp,
                        ),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        LazyColumn {
                            items(homeUiState.value.listOfSearched, key = { it.id }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable {
                                            destinationCode = it.iata_code
                                            showDialog = true
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Place,
                                        contentDescription = "null",
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    Text(
                                        text = it.iata_code,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Your favorite routes",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (homeUiState.value.listOfFavorites.isNotEmpty()) {
                        Spacer(Modifier.height(20.dp))
                        LazyColumn {
                            items(homeUiState.value.listOfFavorites, key = { it.id }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Start: ${it.departure_code}",
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.25f)
                                    )
                                    Text(
                                        text = "--------",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.25f),

                                    )
                                    Text(
                                        text = "End: ${it.destination_code}",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.25f)
                                    )
                                    IconButton(onClick = { viewModel.deleteFavorite(it)}, modifier = Modifier.weight(0.25f)) {
                                        Icon(
                                            imageVector = Icons.Rounded.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Spacer(Modifier.height(40.dp))
                        Text(
                            text = "You have no favorites routes yet!",
                            fontFamily = FontFamily.Default,
                            color = Color.Gray
                        )
                    }
                }
            }

        }
        AnimatedVisibility(
            visible = expanded, modifier = Modifier
                .offset(y = 60.dp)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .width(textFieldSize.width.dp),
                elevation = CardDefaults.cardElevation(15.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 150.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (homeUiState.value.listOfCurrentSearch.isNotEmpty()) {
                        items(homeUiState.value.listOfCurrentSearch) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val newText = it.iata_code + " " + it.name
                                        textFieldValue = textFieldValue.copy(
                                            text = newText,
                                            selection = TextRange(newText.length)
                                        )
                                        expanded = false
                                    }
                            ) {
                                Text(
                                    text = it.iata_code,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                    else if(textFieldValue.text.isEmpty()){
                        if(homeUiState.value.listOfHistory.isEmpty()){
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ){
                                    Text(
                                        text = "No search history!",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }else{
                            item { 
                                Text(
                                    text = "History Search:",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            items(homeUiState.value.listOfHistory){
                                val iata_code = it.substring(0..2)
                                val name = it.substring(3..<it.length)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val newText = it
                                            textFieldValue = textFieldValue.copy(
                                                text = newText,
                                                selection = TextRange(newText.length)
                                            )
                                            expanded = false
                                        }
                                ) {
                                    Text(
                                        text = iata_code,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

private fun obtainIataCode(input: String): String {
    return if (input.isNotEmpty()) input.trim().substring(0..2) else ""
}

@Preview
@Composable
private fun MainScreenPreview() {
    FlightingAppTheme {
        HomeScreen()
    }
}