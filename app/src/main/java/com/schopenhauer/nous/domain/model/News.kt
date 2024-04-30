package com.schopenhauer.nous.domain.model

import com.schopenhauer.nous.data.network.models.NewsApiModel

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

enum class NewsError(val code: String, val message: String) {
	LOAD("SE99", "뉴스를 불러오지 못했어요")
}