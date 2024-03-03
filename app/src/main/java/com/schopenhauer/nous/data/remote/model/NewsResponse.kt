package com.schopenhauer.nous.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
	@SerializedName("lastBuildDate") val lastBuildDate: String,
	@SerializedName("total") val total: Int,
	@SerializedName("start") val start: Int,
	@SerializedName("display") val display: Int,
	@SerializedName("items") val newsApiModels: List<NewsApiModel>,
)

data class NewsApiModel(
	@SerializedName("title") val title: String,
	@SerializedName("link") val link: String,
	@SerializedName("originallink") val originalLink: String,
	@SerializedName("description") val description: String,
	@SerializedName("pubDate") val pubDate: String,
)