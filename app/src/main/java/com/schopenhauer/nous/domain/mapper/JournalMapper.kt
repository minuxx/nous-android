package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.Task

fun Journal.toJournalEntity(): JournalEntity {
	return JournalEntity(
		date = date,
		mainTag = mainTag
	)
}

fun JournalEntity.toJournal(tasks: List<Task> = listOf()): Journal {
	return Journal(
		id = id,
		date = date,
		mainTag = mainTag,
		tasks = tasks
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