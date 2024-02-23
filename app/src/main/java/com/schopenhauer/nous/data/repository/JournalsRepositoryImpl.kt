package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.local.datasource.JournalLocalDataSource
import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.Result
import javax.inject.Inject

class JournalsRepositoryImpl @Inject constructor(
	private val journalLocalDataSource: JournalLocalDataSource
) : JournalsRepository {

	override suspend fun writeJournals(journals: List<JournalEntity>) =
		journalLocalDataSource.insertJournals(journals)

	override suspend fun getJournals() = journalLocalDataSource.getAllJournals()

	override suspend fun saveJournal(date: String, tasks: List<TaskEntity>): Result<Unit> {
		// 1. 저장된 같은 날짜의 업무 일지 있는지 확인
		// 2. JournalEntity 저장 및 다시 가져오기
		// 3. taskEntities 에 journalId 할당 후 저장하기
		return Result.Success(null)
	}

	companion object {
		private const val TAG = "JournalsRepositoryImpl"
	}
}