@file:OptIn(ExperimentalMaterial3Api::class)

package com.vector.flightingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vector.flightingapp.ui.theme.FlightingAppTheme

@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    title: String,
    departureCode: String,
    destinationCode: String,
    onDismissDialog: () -> Unit,
    onFavoriteAction: () -> Unit,
    onConfirmedButtonPressed: () -> Unit,
) {
    var isSavedPressed by remember { mutableStateOf(false) }
    BasicAlertDialog(
        modifier = modifier
            .size(
                height = 300.dp,
                width = 500.dp
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        onDismissRequest = onDismissDialog
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .offset(y = (-20).dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "From: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
//                    textAlign = TextAlign.Center
                )
                Text(
                    text = departureCode,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
//                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "To: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
//                    textAlign = TextAlign.Center
                )
                Text(
                    text = destinationCode,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
//                    textAlign = TextAlign.Center
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DialogButton(
                    onClickPressed = onDismissDialog,
                    buttonText = "Cancel",
                    buttonColor = Color(
                        0xFFC05858
                    )
                )
                Column(
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(horizontal = 4.dp, vertical = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Want to save this route?",
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = {
                        isSavedPressed = !isSavedPressed
                    }) {
                        Icon(
                            imageVector = if (isSavedPressed) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                }
                DialogButton(
                    onClickPressed = {
                        if(isSavedPressed) {
                            onFavoriteAction()
                            onConfirmedButtonPressed()
                        }
                        else{
                            onConfirmedButtonPressed()
                        }
                    },
                    buttonText = "Confirm",
                    buttonColor = Color(0xFF2196F3)
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    modifier: Modifier = Modifier,
    onClickPressed: () -> Unit,
    buttonText: String,
    buttonColor: Color
) {
    Button(
        onClick = onClickPressed,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = modifier.width(100.dp)
    ) {
        Text(text = buttonText)
    }
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    FlightingAppTheme {
        ConfirmDialog(
            title = "Test",
            departureCode = "OME",
            destinationCode = "IPE",
            onDismissDialog = {},
            onConfirmedButtonPressed = {},
            onFavoriteAction = {}
        )
    }
}