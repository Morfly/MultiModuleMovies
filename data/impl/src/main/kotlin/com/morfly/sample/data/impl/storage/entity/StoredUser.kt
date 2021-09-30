package com.morfly.sample.data.impl.storage.entity

import androidx.room.ColumnInfo
import com.morfly.sample.common.domain.User


data class StoredUser(
    @ColumnInfo(name = "id") override val id: String,
    override val name: String,
    @ColumnInfo(name = "image_url") override val imageUrl: String
) : User