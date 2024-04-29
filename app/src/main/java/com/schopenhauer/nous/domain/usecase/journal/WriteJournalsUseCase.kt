package com.schopenhauer.nous.domain.usecase.journal

import com.schopenhauer.nous.domain.mapper.toJournalEntity
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.repository.JournalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WriteJournalsUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(journals: List<Journal>) = journalsRepository.saveJournals(journals)
}