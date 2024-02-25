package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.local.datasource.JournalLocalDataSource
import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.ErrorType.FAIL_SAVE_JOURNAL
import com.schopenhauer.nous.util.ErrorType.ALREADY_SAVED_JOURNAL
import com.schopenhauer.nous.util.Message.SUCCESS_SAVE_JOURNAL
import com.schopenhauer.nous.util.Result
import javax.inject.Inject

class JournalsRepositoryImpl @Inject constructor(
	private val journalLocalDataSource: JournalLocalDataSource
) : JournalsRepository {

	override suspend fun writeJournals(journals: List<JournalEntity>) =
		journalLocalDataSource.insertJournals(journals)

	override suspend fun getJournals() = journalLocalDataSource.getAllJournals()

	override suspend fun saveJournal(date: String, tasks: List<TaskEntity>): Result<String> {
		val hasJournalRes = journalLocalDataSource.hasJournalWithDate(date)
		// 1. 저장된 같은 날짜의 업무 일지 있는지 확인
		if (hasJournalRes is Result.Success && hasJournalRes.data == true) {
			return Result.Error(
				ALREADY_SAVED_JOURNAL.code,
				ALREADY_SAVED_JOURNAL.message
			)
		}

		// 2. JournalEntity 저장 및 ID 가져오기
		val saveJournalRes = journalLocalDataSource.saveJournal(JournalEntity(date = date))
		if (saveJournalRes is Result.Success && saveJournalRes.data != null) {
			// 3. taskEntities 에 journalId 할당 후 저장하기
			val journalId = saveJournalRes.data
			val newTasks = tasks.map { it.copy(journalId = journalId) }
			val saveTasksRes = journalLocalDataSource.saveTasks(newTasks)
			return if (saveTasksRes is Result.Success) {
				Result.Success(SUCCESS_SAVE_JOURNAL.content)
			} else {
				Result.Error(FAIL_SAVE_JOURNAL.code, FAIL_SAVE_JOURNAL.message)
			}
		}

		return Result.Error(FAIL_SAVE_JOURNAL.code, FAIL_SAVE_JOURNAL.message)
	}

	companion object {
		private const val TAG = "JournalsRepositoryImpl"
	}
}