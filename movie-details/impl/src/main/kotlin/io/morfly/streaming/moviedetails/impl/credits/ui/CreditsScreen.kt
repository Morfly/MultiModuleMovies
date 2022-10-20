package io.morfly.streaming.moviedetails.impl.credits.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.moviedetails.impl.R
import io.morfly.streaming.moviedetails.impl.credits.CreditsViewModel
import io.morfly.streaming.moviedetails.impl.credits.Department


private sealed interface State {
    val title: String

    class NoCredits(override val title: String) : State

    class Credits(
        override val title: String,
        val credits: Map<Department, List<Credit>>
    ) : State
}

@Composable
fun CreditsScreen(viewModel: CreditsViewModel) {
    LaunchedEffect(Unit) { viewModel.load() }

    val movieTitle by viewModel.movieTitle.collectAsState()
    val credits by viewModel.credits.collectAsState()

    val context = LocalContext.current

    val state = remember(movieTitle == null, credits.isEmpty()) {
        val title = movieTitle ?: context.getString(R.string.credits)
        when (credits.size) {
            0 -> State.NoCredits(title)
            else -> State.Credits(title, credits)
        }
    }

    RenderState(state)
}

@Composable
private fun RenderState(state: State) {
    Column {
        Toolbar(state.title)

        when (state) {
            is State.Credits -> CreditsList(state.credits)
            is State.NoCredits -> NoCredits()
        }
    }
}
