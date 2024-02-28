package com.schopenhauer.nous.di

import com.schopenhauer.nous.data.repository.JournalsRepositoryImpl
import com.schopenhauer.nous.data.repository.NewsRepositoryImpl
import com.schopenhauer.nous.domain.repository.JournalsRepository
import com.schopenhauer.nous.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
	@Binds
	fun bindsJournalsRepository(journalsRepositoryImpl: JournalsRepositoryImpl): JournalsRepository

	@Binds
	fun bindsNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}
