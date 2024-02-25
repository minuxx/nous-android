package com.schopenhauer.nous.domain.usecase

import com.schopenhauer.nous.domain.repository.JournalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteJournalUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(id: Long) = withContext(Dispatchers.Default) {
		journalsRepository.deleteJournal(id)
	}
}