package com.schopenhauer.nous.util

import android.os.Build
import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

private const val DATE_PATTERN = "yyyy/MM/dd"
private const val TAG = "DateUtil"

fun millisToDate(millis: Long = MaterialDatePicker.todayInUtcMilliseconds()): String =
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		val dateFormat = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.getDefault())
		Instant.ofEpochMilli(millis)
			.atZone(ZoneId.systemDefault())
			.toLocalDate()
			.format(dateFormat)
	} else {
		val dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
		dateFormat.format(Date(millis))
	}

fun parseDate(date: String): Date {
	try {
		val dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
		return dateFormat.parse(date) ?: Date()
	} catch (e: ParseException) {
		throw e
	}
}