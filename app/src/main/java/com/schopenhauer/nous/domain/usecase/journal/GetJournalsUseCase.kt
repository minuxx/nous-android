package com.schopenhauer.nous.domain.usecase.journal

import com.schopenhauer.nous.data.Error
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.JournalError.SORT
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.parseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJournalsUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke() = withContext(Dispatchers.Default) {
		when(val res = journalsRepository.getJournals()) {
			is Result.Success -> {
				try {
					val journals = res.data
					val sortedJournals = journals.sortedByDescending { parseDate(it.date) }

					Result.Success(sortedJournals)
				} catch (e: Exception) {
					Result.Failure(Error.Journal(SORT))
				}
			}
			is Result.Failure -> res
		}
	}

	companion object {
		private const val TAG = "GetJournalsUseCase"
	}
}