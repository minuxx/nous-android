package com.schopenhauer.nous.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.Task

@Entity(tableName = "journals")
data class JournalEntity (
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val timeMillis: Long,
)