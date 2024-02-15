package com.schopenhauer.nous.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journals")
data class JournalEntity (
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val dateTime: String,
	val mainTag: String,
)