package com.schopenhauer.nous.data.local.datasource

import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.entities.JournalEntity
import com.schopenhauer.nous.data.local.entities.TaskEntity
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

	suspend fun getJournal(id: Long) = execute {
		journalDao.getJournal(id)
	}

	suspend fun getTasks(journalId: Long) = execute {
		taskDao.getTasksOfJournal(journalId)
	}

	suspend fun deleteJournal(id: Long) = execute {
		journalDao.deleteJournal(id)
	}

	suspend fun deleteTasks(journalId: Long) = execute {
		taskDao.deleteTasks(journalId)
	}
}