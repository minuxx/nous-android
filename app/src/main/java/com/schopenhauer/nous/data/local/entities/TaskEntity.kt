package com.schopenhauer.nous.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.schopenhauer.nous.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity (
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val journalId: Long = 0,
	val content: String,
)

fun TaskEntity.asDomain(): Task {
	return Task(
		id = id,
		journalId = journalId,
		content = content
	)
}