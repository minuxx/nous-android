package com.schopenhauer.nous.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetNewsesResponse(
	@SerializedName("lastBuildDate") val lastBuildDate: String,
	@SerializedName("total") val total: Int,
	@SerializedName("start") val start: Int,
	@SerializedName("display") val display: Int,
	@SerializedName("items") val items: List<NewsItem>,
)

data class NewsItem(
	@SerializedName("title") val title: String,
	@SerializedName("link") val link: String,
	@SerializedName("originallink") val originalLink: String,
	@SerializedName("description") val description: String,
	@SerializedName("pubDate") val pubDate: String,
)

data class NewsItemPage(
	val newsItems: List<NewsItem>,
	val totalCnt: Int
)