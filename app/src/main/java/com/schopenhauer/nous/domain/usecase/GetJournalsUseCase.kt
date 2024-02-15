package com.schopenhauer.nous.domain.usecase

import com.schopenhauer.nous.domain.mapper.toJournal
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJournalsUseCase @Inject constructor(
	private val journalsRepository: JournalsRepository
) {
	suspend operator fun invoke() = withContext(Dispatchers.Default) {
		when(val res = journalsRepository.getJournals()) {
			is Result.Success -> Result.Success(res.data?.map { it.toJournal() } ?: emptyList())
			is Result.Error -> res
		}
	}
}