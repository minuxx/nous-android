package com.schopenhauer.nous.domain.model

data class Task(
	val id: Int,
	val content: String,
	val dateTime: String,
	val tag: String,
)
