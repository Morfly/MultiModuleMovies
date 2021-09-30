package com.morfly.sample.profile.impl.settings.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.morfly.sample.profile.impl.settings.SettingsViewModel


@Composable
fun SettingsItem(data: SettingsViewModel.SettingsItem) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = data.name,
            Modifier.weight(weight = 1f, fill = true)
        )
        Switch(checked = data.activated, onCheckedChange = null)
    }
}