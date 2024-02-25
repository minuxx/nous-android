package com.schopenhauer.nous.domain.model

data class Task(
	val id: Long,
	val journalId: Long = 0,
	val content: String
)
