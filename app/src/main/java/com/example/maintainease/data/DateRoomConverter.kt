package com.example.maintainease.data


import androidx.room.TypeConverter
import java.util.Date

class DateRoomConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(milliseconds: Long?): Date? {
        return milliseconds?.let { Date(it) }
    }
}