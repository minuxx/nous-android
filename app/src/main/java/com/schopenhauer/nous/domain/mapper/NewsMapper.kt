package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.network.models.NewsItem
import com.schopenhauer.nous.domain.model.News

fun NewsItem.toNews(): News {
	return News(
		id = link,
		title = title,
		description = description,
		link = originalLink,
		date = pubDate
	)
}