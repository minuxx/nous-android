package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.local.entities.JournalEntity
import com.schopenhauer.nous.data.local.entities.TaskEntity
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.Task



fun Task.toTaskEntity(): TaskEntity {
	return TaskEntity(
		content = content
	)
}