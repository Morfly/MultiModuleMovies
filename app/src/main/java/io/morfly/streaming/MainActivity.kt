package io.morfly.streaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import io.morfly.streaming.common.di.LocalCommonProvider
import io.morfly.streaming.common.ui.NavigationBar
import io.morfly.streaming.common.ui.StatusBar
import io.morfly.streaming.common.ui.theme.AppBlack
import io.morfly.streaming.common.ui.theme.MovieStreamingTheme
import io.morfly.streaming.data.LocalDataProvider
import io.morfly.streaming.di.LocalAppProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieStreamingTheme {
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