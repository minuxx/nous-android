package com.schopenhauer.nous.domain.model

data class Task(
	val id: Int,
	val journalId: Int = 0,
	val content: String
)
