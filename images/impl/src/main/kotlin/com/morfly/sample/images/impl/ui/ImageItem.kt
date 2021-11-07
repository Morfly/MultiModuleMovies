/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morfly.sample.images.impl.ui

import android.graphics.Bitmap
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.ui.findDominantColor
import com.morfly.sample.common.ui.glow
import com.morfly.sample.common.ui.theme.AppBlack
import com.morfly.sample.images.impl.R


val USER_IMAGE_SIZE = 43.dp

@OptIn(ExperimentalCoilApi::class)
@Composable
inline fun ImageItem(
    image: Image,
    crossinline onUserSelected: (userId: String) -> Unit
) {
    var glowColor by remember { mutableStateOf(Color.White) }
    val animatedGlowColor by animateColorAsState(
        targetValue = glowColor,
        animationSpec = tween(1500)
    )
    Box(
        modifier = Modifier
            .glow(
                color = animatedGlowColor,
                radius = 25.dp,
                alpha = 0.1f,
            )
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clip(RoundedCornerShape(7))
            .background(Color.White)

    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Row(
                Modifier
                    .height(USER_IMAGE_SIZE)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = image.user.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(USER_IMAGE_SIZE)
                        .clip(RoundedCornerShape(50))
                        .clickable { onUserSelected(image.user.id) }
                )
                Spacer(Modifier.width(10.dp))

                Column(
                    Modifier
                        .padding(vertical = 2.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onUserSelected(image.user.id) }
                ) {
                    Text(text = image.user.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Text(text = "San Francisco", fontSize = 12.sp, fontWeight = FontWeight.Light)
                }
                Spacer(Modifier.weight(1f))
                Box(Modifier.padding(horizontal = 5.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_more_horiz_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { }
                            .padding(5.dp)
                    )
                }
            }
            Box(
                Modifier
                    .padding(12.dp)
                    .glow(
                        color = animatedGlowColor,
                        radius = 25.dp,
                        alpha = 0.3f,
                        offsetY = 15.dp
                    )
            ) {
                PhotoImage(url = image.url) {
                    glowColor = it
                }
            }
            Row(Modifier.padding(horizontal = 12.dp)) {
                Likes(likes = image.likes.toString())
                Spacer(Modifier.width(7.dp))
                Comments(comments = image.comments.toString())
                Spacer(Modifier.width(7.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_bookmark_border_24),
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

@OptIn(ExperimentalCoilApi::class, ExperimentalAnimationApi::class)
@Composable
inline fun PhotoImage(url: String, crossinline onDominantColorLoaded: (Color) -> Unit) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true) // FIXME: cross fade does not work
            allowHardware(false)
        },
        onExecute = { _, curr ->
            when (val state = curr.state) {
                is ImagePainter.State.Success -> {
                    val bitmap = (state.painter as? BitmapPainter)?.bitmap()
                    val color = bitmap?.findDominantColor()
                    color?.let(onDominantColorLoaded)
                }
            }
            true
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .requiredHeight(240.dp)
    )
}

@Composable
fun Likes(likes: String) {
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
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = likes,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppBlack
                )
            }
        }
    }
}

@Composable
fun Comments(comments: String) {
    Row(
        Modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .width(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_round_chat_24),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = comments,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AppBlack
            )
        }
    }
}

fun BitmapPainter.bitmap(): Bitmap {
    val field = javaClass.getDeclaredField("image")
    field.isAccessible = true
    return (field.get(this) as ImageBitmap).asAndroidBitmap()
}