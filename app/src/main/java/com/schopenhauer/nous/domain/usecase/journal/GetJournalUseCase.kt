package com.schopenhauer.nous.domain.usecase.journal

import com.schopenhauer.nous.domain.mapper.toJournal
import com.schopenhauer.nous.domain.mapper.toTask
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJournalUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke(id: Long) = withContext(Dispatchers.Default) {
		when(val res = journalsRepository.getJournal(id)) {
			is Result.Success -> {
				val tasks = res.data.tasks.map { it.toTask() }
				val journal = res.data.journal.toJournal(tasks)
				Result.Success(journal)
			}
			is Result.Error -> res
		}
	}
}