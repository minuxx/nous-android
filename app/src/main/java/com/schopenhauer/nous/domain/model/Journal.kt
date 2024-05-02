package com.schopenhauer.nous.domain.model

import com.schopenhauer.nous.data.local.entities.JournalEntity

data class Journal (
	val id: Long,
	val timeMillis: Long,
	val tasks: List<Task>
) {
	companion object {
		fun create(timeMillis: Long?, tasks: List<Task>): Journal {
			validateTasks(tasks)
			validateTimeMillis(timeMillis)

			return Journal(id = 0, timeMillis = timeMillis!!, tasks = tasks)
		}

		private fun validateTimeMillis(timeMillis: Long?) {
			if (timeMillis == null) {
				throw JournalValidationException(JournalError.EMPTY_DATE)
			}
		}

		private fun validateTasks(tasks: List<Task>) {
			if (tasks.isEmpty()) {
				throw JournalValidationException(JournalError.EMPTY_TASK)
			}
		}
	}
}

class JournalValidationException(val error: JournalError) : Exception(error.message)

enum class JournalError(val code: String, val message: String) {
	LOAD("J400", "업무 일지들을 가져오지 못했어요"),
	SAVE("J401", "업무 일지를 저장하지 못했어요"),
	ALREADY("J402", "해당 날짜에 업무 일지가 이미 존재해요"),
	REMOVE("J403", "업무 일지를 삭제하지 못했어요"),
	SORT("J404", "업무 일지를 정렬하지 못했어요"),

	EMPTY_DATE("J405", "날짜를 선택해주세요"),
	EMPTY_TASK("J406", "업무를 입력해주세요"),
}

fun Journal.asEntity(): JournalEntity {
	return JournalEntity(
		timeMillis = timeMillis,
	)
}