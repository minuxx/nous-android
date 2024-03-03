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