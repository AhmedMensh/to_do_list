package com.example.to_do_list.data.local.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimeStamp(value : Long) : Date = Date(value)

    @TypeConverter
    fun dateToTimeStamp(date : Date) : Long = date.time
}