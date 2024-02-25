package com.schopenhauer.nous.data.local.datasource

import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity
import javax.inject.Inject

class JournalLocalDataSource @Inject constructor(
	private val journalDao: JournalDao,
	private val taskDao: TaskDao
) : BaseLocalDataSource() {
	suspend fun insertJournals(journals: List<JournalEntity>) = execute {
		journalDao.insertJournals(journals)
	}

	suspend fun getAllJournals() = execute {
		journalDao.getAllJournals()
	}

	suspend fun hasJournalWithDate(date: String) = execute {
		val journalCount = journalDao.getJournalCountByDate(date)
		journalCount > 0
	}

	suspend fun saveJournal(journal: JournalEntity) = execute {
		journalDao.insertJournal(journal)
	}

	suspend fun saveTasks(tasks: List<TaskEntity>) = execute {
		taskDao.insertTasks(tasks)
	}
}