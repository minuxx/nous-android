package com.schopenhauer.nous.util

import android.os.Build
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun millisToDate(millis: Long = MaterialDatePicker.todayInUtcMilliseconds()): String =
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		val dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())
		Instant.ofEpochMilli(millis)
			.atZone(ZoneId.systemDefault())
			.toLocalDate()
			.format(dateFormat)
	} else {
		val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
		dateFormat.format(Date(millis))
	}