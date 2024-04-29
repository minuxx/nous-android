package com.schopenhauer.nous.domain.model

data class Journal (
	val id: Long,
	val date: String,
	val mainTag: String,
	val tasks: List<Task>
)

data class Task(
	val id: Long,
	val journalId: Long = 0,
	val content: String
)

enum class JournalFailure : Failure {
	LOAD_JOURNALS {
		override fun code() = "J4000"
		override fun message() = "업무 일지들을 가져오는 실패했어요"
	},
	SAVE_JOURNAL {
		override fun code() = "J4001"
		override fun message() = "업무 일지를 저장하지 못했어요"
	};
}