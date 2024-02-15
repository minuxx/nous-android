package com.schopenhauer.nous.domain.mapper

import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.domain.model.Journal

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