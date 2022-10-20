package io.morfly.streaming.moviesearch.impl.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.ui.ProgressiveGlowingImage
import io.morfly.streaming.common.ui.theme.AppBlack
import io.morfly.streaming.common.R as CommonR


@Composable
fun MovieDetailsCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onExpandMovieDetails: (Movie) -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clip(RoundedCornerShape(7))
            .background(Color.White)
            .clickable { onExpandMovieDetails(movie) }
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Row(
                Modifier
                    .height(40.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(10.dp))

                Text(
                    text = movie.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .weight(1f),
                )
                Box(Modifier.padding(horizontal = 5.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { onClose() }
                            .padding(5.dp)
                    )
                }
            }
            val backdropUrl = movie.backdropUrl
            val thumbBackdropUrl = movie.thumbBackdropUrl
            if (backdropUrl != null && thumbBackdropUrl != null) {
                ProgressiveGlowingImage(backdropUrl, thumbBackdropUrl, glow = true)
            }
            Text(
                text = movie.overview,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 22.dp, end = 12.dp)
            )
            Row(Modifier.padding(horizontal = 12.dp)) {
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
            Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = voteAverage,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppBlack
                )
            }
        }
    }
}
