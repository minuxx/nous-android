package com.schopenhauer.nous.util

import android.os.Build
import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_PATTERN = "yyyy/MM/dd"
private const val TIME_PATTERN = "HH:mm"
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

fun formatNewsDate(inputDate: String): String {
	return try {
		val dateFormat: DateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault())
			val offsetDateTime = OffsetDateTime.parse(inputDate, formatter)
			val zonedDateTime = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault())
			SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).apply {
				timeZone = TimeZone.getTimeZone(zonedDateTime.zone)
			}
		} else {
			SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault())
		}
		val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())

		val date = dateFormat.parse(inputDate)
		outputFormat.format(date)
	} catch (e: Exception) {
		Log.e(TAG, "${e.message}")
		""
	}
}
