package com.example.taskmanagement.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.example.taskmanagement.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TaskUtils {

    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.US)

    fun showDatePickerDialog(context: Context, etDateDeadline: EditText) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, selectedYear, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val formattedDate = dateFormat.format(calendar.time)
            etDateDeadline.setText(formattedDate)
        }

        val datePickerDialog = DatePickerDialog(context, datePickerListener, year, month, day)
        datePickerDialog.show()
    }

    fun showTimePickerDialog(context: Context, etTimeDeadline: EditText) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)

            val formattedTime = timeFormat.format(calendar.time)
            etTimeDeadline.setText(formattedTime)
        }

        val timePickerDialog = TimePickerDialog(context, timePickerListener, hour, minute, true)
        timePickerDialog.show()
    }

    fun setupAutoComplete(context: Context, autoCompletePriorityLevel: AutoCompleteTextView) {
        val priorityLevels = listOf("High", "Medium", "Low")
        val adapterItems = ArrayAdapter(context, R.layout.list_item, priorityLevels)
        autoCompletePriorityLevel.setAdapter(adapterItems)
    }
}