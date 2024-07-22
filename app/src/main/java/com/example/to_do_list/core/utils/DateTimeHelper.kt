package com.example.to_do_list.core.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import com.example.to_do_list.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateTimeHelper {

    fun showDateTimePicker(context: Context, onDateSelected: (Date) -> Unit) {
        val currentDate = Calendar.getInstance()
        val date = Calendar.getInstance()
        DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                onDateSelected(date.time)
            },
            currentDate[Calendar.YEAR],
            currentDate[Calendar.MONTH],
            currentDate[Calendar.DAY_OF_MONTH]
        ).show()


    }

    fun format(date: Date?): String {
        date?.let {
            val sdf = SimpleDateFormat("yyyy-M-dd", Locale.getDefault())
            return sdf.format(date)
        }
      return date.toString()
    }

    fun isBeforeCurrentDate(endDate: Date?): Boolean {
        return endDate?.before(Date()) ?: false
    }
}