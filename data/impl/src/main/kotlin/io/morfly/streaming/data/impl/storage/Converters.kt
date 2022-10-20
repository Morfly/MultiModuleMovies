package io.morfly.streaming.data.impl.storage

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


class Converters {

    private val sdf by lazy { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.'Z'", Locale.getDefault()) }

    @TypeConverter
    fun stringToDate(value: String?): Date? {
        return value?.let(sdf::parse)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return date?.let(sdf::format)
    }
}