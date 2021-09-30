package com.morfly.sample.archcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import com.morfly.sample.common.di.LocalCommonProvider
import com.morfly.sample.common.ui.NavigationBar
import com.morfly.sample.common.ui.StatusBar
import com.morfly.sample.common.ui.theme.AppBlack
import com.morfly.sample.common.ui.theme.ComposeArchTheme
import com.morfly.sample.data.LocalDataProvider
import com.morfly.sample.archcompose.di.LocalAppProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeArchTheme {
                StatusBar(window, color = MaterialTheme.colors.background)

                Surface(color = MaterialTheme.colors.background) {
                    CompositionLocalProvider(
                        LocalAppProvider provides application.appProvider,
                        LocalDataProvider provides application.appProvider,
                        LocalCommonProvider provides application.appProvider
                    ) {
                        Navigation()
                    }
                }

                NavigationBar(window, color = AppBlack)
            }
        }
    }
}