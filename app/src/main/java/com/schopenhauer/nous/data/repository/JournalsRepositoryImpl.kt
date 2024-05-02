package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.Error
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.entities.asDomain
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.JournalError.ALREADY
import com.schopenhauer.nous.domain.model.JournalError.LOAD
import com.schopenhauer.nous.domain.model.JournalError.REMOVE
import com.schopenhauer.nous.domain.model.JournalError.SAVE
import com.schopenhauer.nous.domain.model.JournalValidationException
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.model.asEntity
import com.schopenhauer.nous.domain.repository.JournalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JournalsRepositoryImpl @Inject constructor(
	private val journalDao: JournalDao,
	private val taskDao: TaskDao
) : JournalsRepository {

	override suspend fun getJournals() = withContext(Dispatchers.IO) {
		try {
			val journals = journalDao.getJournals()
			Result.Success(journals.map { it.asDomain() })
		} catch (e: Exception) {
			Result.Failure(Error.Journal(LOAD))
		}
	}

	override suspend fun getJournal(id: Long) = withContext(Dispatchers.IO) {
		try {
			val journal = journalDao.getJournal(id)
			Result.Success(journal.asDomain())
		} catch (e: Exception) {
			Result.Failure(Error.Journal(LOAD))
		}
	}

	override suspend fun saveJournal(timeMillis: Long?, tasks: List<Task>) = withContext(Dispatchers.IO) {
		try {
			val journal = Journal.create(timeMillis, tasks)

			// 저장된 같은 날짜의 업무 일지 있는지 확인
			val journalCountOfSameDate = journalDao.getJournalCountByDate(journal.timeMillis)
			if (journalCountOfSameDate > 0) {
				Result.Failure(Error.Journal(ALREADY))
			} else {
				val journalId = journalDao.insertJournal(journal = journal.asEntity())
				taskDao.insertTasks(tasks.map { it.asEntity(journalId) })

				Result.Success(Unit)
			}
		} catch (e: JournalValidationException) {
			Result.Failure(Error.Journal(e.error))
		} catch (e: Exception) {
			Result.Failure(Error.Journal(SAVE))
		}
	}

	override suspend fun removeJournal(id: Long) = withContext(Dispatchers.IO) {
		try {
			journalDao.deleteJournal(id)
			taskDao.deleteTasks(id)

			Result.Success(Unit)
		} catch (e: Exception) {
			Result.Failure(Error.Journal(REMOVE))
		}
	}

	companion object {
		private const val TAG = "JournalsRepositoryImpl"
	}
}