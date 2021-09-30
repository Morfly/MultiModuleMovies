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

package com.morfly.sample.data.impl.mapping

import com.morfly.sample.data.impl.network.model.PixabayImage
import com.morfly.sample.data.impl.storage.entity.StoredImage
import com.morfly.sample.data.impl.storage.entity.StoredUser
import javax.inject.Inject


class DefaultDataMapper @Inject constructor() : DataMapper {

    override fun networkToStorage(image: PixabayImage, query: String) = with(image) {
        StoredImage(
            id = id,
            query = query,
            url = largeImageUrl,
            previewUrl = previewUrl,
            tags = tags,
            views = views,
            downloads = downloads,
            favorites = favorites ?: 0,
            likes = likes,
            comments = comments,
            timestamp = System.nanoTime(),
            user = StoredUser(
                id = userId,
                name = user,
                imageUrl = userImageUrl
            )
        )
    }
}