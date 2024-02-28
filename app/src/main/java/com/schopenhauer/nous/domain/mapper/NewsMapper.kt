package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.remote.model.NewsApiModel
import com.schopenhauer.nous.domain.model.News

fun NewsApiModel.toNews(): News {
	return News(
		id = originalLink,
		title = title,
		description = description,
		link = originalLink,
		date = pubDate
	)
}