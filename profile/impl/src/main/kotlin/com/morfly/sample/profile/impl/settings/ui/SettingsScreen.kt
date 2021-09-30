package com.morfly.sample.profile.impl.settings.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.morfly.sample.profile.impl.settings.SettingsViewModel


@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {

    val settingsItems = viewModel.settings

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp),
        ) {
            Text(text = "Settings", style = MaterialTheme.typography.subtitle1)
        }
        LazyColumn {
            items(settingsItems.size) { i ->
                SettingsItem(settingsItems[i])

                if (i < settingsItems.lastIndex)
                    Divider(color = Color.LightGray)
            }
        }
    }
}