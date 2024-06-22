package com.schopenhauer.nous.domain.model

import com.schopenhauer.nous.data.local.entities.TaskEntity

data class Task(
	val id: Long,
	val content: String,
) {
	companion object {
		fun create(id: Long, content: String?): Task {
			validateContent(content)

			return Task(id = id,content = content!!)
		}

		private fun validateContent(content: String?) {
			if (content.isNullOrBlank()) {
				throw TaskValidationException(TaskError.EMPTY_CONTENT)
			}
		}
	}
}

class TaskValidationException(val error: TaskError) : Exception(error.message)

enum class TaskError(val code: String, val message: String) {
	LOAD("T400", "해야 할 일 목록을 가져오는 데 실패했어요"),
	SAVE("T401", "해야 할 일을 저장하지 못했어요"),

	EMPTY_CONTENT("T402", "업무 내용을 입력해주세요")
}

fun Task.asEntity(journalId: Long = 0): TaskEntity {
	return TaskEntity(
		id = id,
		content = content,
		journalId = journalId
	)
}