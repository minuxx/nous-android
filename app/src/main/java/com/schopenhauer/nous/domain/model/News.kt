package com.schopenhauer.nous.domain.model

data class News (
	val id: String,
	val title: String,
	val description: String,
	val date: String,
	val link: String,
)

data class NewsPage(
	val newses: List<News>,
	val totalCnt: Int
)

enum class NewsFailure : Failure {
	LOAD_JOURNALS {
		override fun code() = "J4000"
		override fun message() = "업무 일지들을 가져오는 실패했어요"
	},
	SAVE_JOURNAL {
		override fun code() = "J4001"
		override fun message() = "업무 일지를 저장하지 못했어요"
	};
}