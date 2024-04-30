package com.schopenhauer.nous.domain.model

import com.schopenhauer.nous.data.local.entities.TaskEntity

data class Task(
	val id: Long,
	val journalId: Long = 0,
	val content: String
)

class TaskValidationException(val error: JournalError) : Exception(error.message)

enum class TaskError(val code: String, val message: String) {
	LOAD("T400", "해야 할 일 목록을 가져오는 데 실패했어요"),
	SAVE("T401", "해야 할 일을 저장하지 못했어요"),
}

fun Task.asEntity(journalId: Long = 0): TaskEntity {
	return TaskEntity(
		journalId = journalId,
		content = content
	)
}