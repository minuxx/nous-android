package com.schopenhauer.nous.domain.model

data class Item(
	val id: Long = 0,
	val thumbnailUrl: String = "",
	val isBookmarked: Boolean = false,
	val page: Int? = null
) {
	fun page() = page.toString()
}