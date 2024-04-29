package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.Error
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.entities.JournalEntity
import com.schopenhauer.nous.data.local.entities.asDomain
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.JournalError.ALREADY
import com.schopenhauer.nous.domain.model.JournalError.LOAD
import com.schopenhauer.nous.domain.model.JournalError.REMOVE
import com.schopenhauer.nous.domain.model.JournalError.SAVE
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

	override suspend fun saveJournals(journals: List<Journal>) = withContext(Dispatchers.IO) {
		try {
			journalDao.insertJournals(journals.map { it.asEntity() })

			Result.Success(Unit)
		} catch (e: Exception) {
			Result.Failure(Error.Journal(SAVE))
		}
	}

	override suspend fun getJournals() = withContext(Dispatchers.IO) {
		try {
			val journals = journalDao.getAllJournals()

			Result.Success(journals.map { it.asDomain() })
		} catch (e: Exception) {
			Result.Failure(Error.Journal(LOAD))
		}
	}

	override suspend fun saveJournal(date: String, tasks: List<Task>) = withContext(Dispatchers.IO) {
		try {
			// 저장된 같은 날짜의 업무 일지 있는지 확인
			val journal = journalDao.getJournalCountByDate(date)
			if (journal > 0) {
				Result.Failure(Error.Journal(ALREADY)) // FIXME 테스트
			}

			// JournalEntity 저장 및 ID 가져오기
			val journalId = journalDao.insertJournal(JournalEntity(date = date))
			// journalId 할당 후 태스크 목록 저장하기
			val newTasks = tasks.map { it.asEntity(journalId) }
			taskDao.insertTasks(newTasks)

			Result.Success(Unit)
		} catch (e: Exception) {
			Result.Failure(Error.Journal(SAVE))
		}
	}

	override suspend fun getJournal(id: Long) = withContext(Dispatchers.IO) {
		try {
			val taskEntities = taskDao.getTasksOfJournal(id)
			val journalEntity = journalDao.getJournal(id)

			val tasks = taskEntities.map { it.asDomain() }
			val journal = journalEntity.asDomain(tasks)

			Result.Success(journal)
		} catch (e: Exception) {
			Result.Failure(Error.Journal(LOAD))
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