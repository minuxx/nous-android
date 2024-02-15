package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.local.datasource.JournalLocalDataSource
import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.domain.repository.JournalsRepository
import javax.inject.Inject

class JournalsRepositoryImpl @Inject constructor(
	private val journalLocalDataSource: JournalLocalDataSource
) : JournalsRepository {

	override suspend fun writeJournals(journals: List<JournalEntity>) =
		journalLocalDataSource.insertJournals(journals)

	override suspend fun getJournals() = journalLocalDataSource.getAllJournals()

	companion object {
		private const val TAG = "JournalsRepositoryImpl"
	}
}