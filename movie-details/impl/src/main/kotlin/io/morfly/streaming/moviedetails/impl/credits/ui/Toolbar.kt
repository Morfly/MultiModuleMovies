package io.morfly.streaming.moviedetails.impl.credits.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun Toolbar(movieTitle: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 15.dp, bottom = 10.dp)
    ) {
        Text(
            movieTitle,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}