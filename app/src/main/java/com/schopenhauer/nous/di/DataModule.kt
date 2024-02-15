package com.schopenhauer.nous.di

import com.schopenhauer.nous.data.repository.JournalsRepositoryImpl
import com.schopenhauer.nous.domain.repository.JournalsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
	@Binds
	fun bindsJournalsRepository(journalsRepositoryImpl: JournalsRepositoryImpl): JournalsRepository
}
