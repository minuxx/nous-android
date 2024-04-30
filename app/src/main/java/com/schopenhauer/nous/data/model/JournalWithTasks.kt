package com.schopenhauer.nous.data.model

import com.schopenhauer.nous.data.local.entities.JournalEntity
import com.schopenhauer.nous.data.local.entities.TaskEntity

data class JournalWithTasks(
	val journal: JournalEntity,
	val tasks: List<TaskEntity>
)