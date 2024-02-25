package com.schopenhauer.nous.data.repository.model

import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity

data class JournalWithTasks(
	val journal: JournalEntity,
	val tasks: List<TaskEntity>
)