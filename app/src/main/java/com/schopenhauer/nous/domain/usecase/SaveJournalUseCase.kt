package com.schopenhauer.nous.domain.usecase

import com.schopenhauer.nous.domain.mapper.toTaskEntity
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveJournalUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(date: String, tasks: List<Task>) = withContext(Dispatchers.Default) {
		when(val res = journalsRepository.saveJournal(date, tasks.map { it.toTaskEntity() })) {
			is Result.Success -> Result.Success()
			is Result.Error -> res
		}
	}
}