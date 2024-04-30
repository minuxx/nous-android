package com.schopenhauer.nous.domain.usecase.journal

import com.schopenhauer.nous.domain.repository.JournalsRepository
import javax.inject.Inject

class GetJournalUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(id: Long) = journalsRepository.getJournal(id)
}