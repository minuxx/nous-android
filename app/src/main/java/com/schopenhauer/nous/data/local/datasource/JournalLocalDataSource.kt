package com.schopenhauer.nous.data.local.datasource

import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.model.JournalEntity
import javax.inject.Inject

class JournalLocalDataSource @Inject constructor(
	private val journalDao: JournalDao
) : BaseLocalDataSource() {
	suspend fun insertJournals(journals: List<JournalEntity>) = execute {
		journalDao.insertJournals(journals)
	}

	suspend fun getAllJournals() = execute {
		journalDao.getAllJournals()
	}

	suspend fun hasJournalWithDate(date: String) = execute {
		val journalCount = journalDao.getJournalCountByDate(date)
		journalCount >= 1
	}
}