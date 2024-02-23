package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.Task

fun Journal.toJournalEntity(): JournalEntity {
	return JournalEntity(
		dateTime = dateTime,
		mainTag = mainTag
	)
}

fun JournalEntity.toJournal(): Journal {
	return Journal(
		id = id,
		dateTime = dateTime,
		mainTag = mainTag
	)
}

fun Task.toTaskEntity(): TaskEntity {
	return TaskEntity(
		content = content
	)
}

fun TaskEntity.toTask(): Task {
	return Task(
		id = id,
		journalId = journalId,
		content = content
	)
}