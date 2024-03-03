package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.remote.model.NewsItem
import com.schopenhauer.nous.domain.model.News

fun NewsItem.toNews(): News {
	return News(
		id = originalLink,
		title = title,
		description = description,
		link = originalLink,
		date = pubDate
	)
}