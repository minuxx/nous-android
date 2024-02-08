package com.schopenhauer.nous.data.remote.model

import com.google.gson.annotations.SerializedName

data class ListResponse(
	@SerializedName("list") val items: List<Item>,
	@SerializedName("meta") val meta: ItemMeta
)

data class Item(
	@SerializedName("id") val id: Long,
	@SerializedName("thumbnailUrl") val thumbnailUrl: String,
	@SerializedName("isBookmarked") val isBookmarked: Boolean,
)

data class ItemMeta(
	@SerializedName("total_count") val totalCount: Int,
	@SerializedName("is_end") val isEnd: Boolean
)

data class ItemPage(
	val items: List<Item>,
	val isLast: Boolean
)