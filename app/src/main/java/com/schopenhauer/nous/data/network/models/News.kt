package com.schopenhauer.nous.data.network.models

import com.google.gson.annotations.SerializedName
import com.schopenhauer.nous.domain.model.News

data class GetNewsesResponse(
	@SerializedName("lastBuildDate") val lastBuildDate: String,
	@SerializedName("total") val total: Int,
	@SerializedName("start") val start: Int,
	@SerializedName("display") val display: Int,
	@SerializedName("items") val items: List<NewsApiModel>,
)

data class NewsApiModel(
	@SerializedName("title") val title: String,
	@SerializedName("link") val link: String,
	@SerializedName("originallink") val originalLink: String,
	@SerializedName("description") val description: String,
	@SerializedName("pubDate") val pubDate: String,
)

fun NewsApiModel.asDomain(): News {
	return News(
		id = link,
		title = title,
		description = description,
		link = originalLink,
		date = pubDate
	)
}