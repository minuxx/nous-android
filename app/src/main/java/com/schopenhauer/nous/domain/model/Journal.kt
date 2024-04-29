package com.schopenhauer.nous.domain.model

import com.schopenhauer.nous.data.local.entities.JournalEntity

data class Journal (
	val id: Long,
	val date: String,
	val mainTag: String,
	val tasks: List<Task>
)

class JournalValidationException(val error: JournalError) : Exception(error.message)

enum class JournalError(val code: String, val message: String) {
	LOAD("J400", "업무 일지들을 가져오지 못했어요"),
	SAVE("J401", "업무 일지를 저장하지 못했어요"),
	ALREADY("J402", "해당 날짜에 업무 일지가 이미 존재해요"),
	REMOVE("J403", "업무 일지를 삭제하지 못했어요"),

	SORT("J404", "업무 일지를 정렬하지 못했어요")
}

fun Journal.asEntity(): JournalEntity {
	return JournalEntity(
		date = date,
		mainTag = mainTag
	)
}