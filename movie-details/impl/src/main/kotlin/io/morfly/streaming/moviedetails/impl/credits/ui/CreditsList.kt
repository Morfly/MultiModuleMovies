package io.morfly.streaming.moviedetails.impl.credits.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.common.ui.Dimens
import io.morfly.streaming.moviedetails.impl.credits.Department


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreditsList(groupedCredits: Map<Department, List<Credit>>) {
    LazyColumn(Modifier.padding(bottom = Dimens.bottomPadding)) {
        for ((department, credits) in groupedCredits) {

            stickyHeader {
                DepartmentItem(name = department)
            }
            items(credits.size) { i ->
                NameItem(name = credits[i].name)
            }
        }
    }
}

@Composable
fun DepartmentItem(name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 15.dp, bottom = 10.dp)
    ) {
        Text(
            name,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun NameItem(name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(name, style = MaterialTheme.typography.body1)
    }
}