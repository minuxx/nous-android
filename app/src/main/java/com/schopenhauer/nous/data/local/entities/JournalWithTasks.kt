package com.schopenhauer.nous.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.schopenhauer.nous.domain.model.Journal

data class JournalWithTasks(
	@Embedded val journal: JournalEntity,
	@Relation(
		parentColumn = "id",
		entityColumn = "journalId"
	)
	val tasks: List<TaskEntity>
)

fun JournalWithTasks.asDomain(): Journal {
	return Journal(
		id = journal.id,
		timeMillis = journal.timeMillis,
		tasks = tasks.map { it.asDomain() }
	)
}
