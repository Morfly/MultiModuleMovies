package io.morfly.streaming.moviedetails.impl.movie.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.ui.Dimens.horizontalPadding
import io.morfly.streaming.common.ui.ProgressiveGlowingImage
import io.morfly.streaming.common.ui.theme.AppBlack
import io.morfly.streaming.moviedetails.impl.R
import io.morfly.streaming.common.R as CommonR


@Composable
fun Movie(movie: Movie, onShowCredits: () -> Unit) {
    Column(Modifier.padding(vertical = 12.dp)) {
        val backdropUrl = movie.backdropUrl
        val thumbBackdropUrl = movie.thumbBackdropUrl
        if (backdropUrl != null && thumbBackdropUrl != null) {
            ProgressiveGlowingImage(backdropUrl, thumbBackdropUrl, glow = false)
        }

        Text(
            movie.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 10.dp, horizontal = horizontalPadding)
        )

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )
        val context = LocalContext.current
        Text(
            text = context.getString(R.string.show_credits),
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onShowCredits() }
                .padding(horizontal = horizontalPadding, vertical = 10.dp)
        )
        Row(Modifier.padding(horizontal = horizontalPadding)) {
            Rating(voteAverage = movie.voteAverage.toString())
            Spacer(Modifier.width(7.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 3.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(CommonR.drawable.ic_round_bookmark_border_24),
                    contentDescription = null,
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable { }
                        .padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun Rating(voteAverage: String) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colors.background)
            .width(80.dp)
    ) {
        Row(
            Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null
            )
            Text(
                text = voteAverage,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                color = AppBlack,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}