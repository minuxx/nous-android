package com.schopenhauer.nous.domain.model

data class Journal (
	val id: Long,
	val date: String,
	val mainTag: String,
	val tasks: List<Task>
)