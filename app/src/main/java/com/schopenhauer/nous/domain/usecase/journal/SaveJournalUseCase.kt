package com.schopenhauer.nous.domain.usecase.journal

import com.schopenhauer.nous.domain.mapper.toTaskEntity
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.repository.JournalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveJournalUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(date: String, tasks: List<Task>) = withContext(Dispatchers.Default) {
		journalsRepository.saveJournal(date, tasks.map { it.toTaskEntity() })
	}
}