package com.schopenhauer.nous.domain.usecase.journal

import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.repository.JournalsRepository
import javax.inject.Inject

class SaveJournalUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(timeMillis: Long?, tasks: List<Task>) = journalsRepository.saveJournal(timeMillis, tasks)
}