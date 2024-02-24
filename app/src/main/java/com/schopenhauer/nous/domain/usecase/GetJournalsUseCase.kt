package com.schopenhauer.nous.domain.usecase

import android.util.Log
import com.schopenhauer.nous.domain.mapper.toJournal
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.ErrorType.PARSE
import com.schopenhauer.nous.util.Result
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
					val journals = res.data?.map { it.toJournal() } ?: emptyList()
					val sortedJournals = journals.sortedByDescending { parseDate(it.date) }
					Result.Success(sortedJournals)
				} catch (e: Exception) {
					Log.e(TAG, "${PARSE.message}, Message: ${e.message}")
					Result.Error(PARSE.code, PARSE.message)
				}
			}
			is Result.Error -> res
		}
	}

	companion object {
		private const val TAG = "GetJournalsUseCase"
	}
}