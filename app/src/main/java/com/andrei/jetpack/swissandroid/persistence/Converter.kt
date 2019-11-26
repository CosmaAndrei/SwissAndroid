package com.andrei.jetpack.swissandroid.persistence

import androidx.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToListOfString(value: String): List<String> =
        value.replace("[", "").replace("]", "").split(",")

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}