package com.schopenhauer.nous.di

import android.content.Context
import com.schopenhauer.nous.data.local.NousDatabase
import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

	@Singleton
	@Provides
	fun provideNous(@ApplicationContext context: Context): NousDatabase =
		NousDatabase.buildDatabase(context)

	@Singleton
	@Provides
	fun provideTaskDao(nousDatabase: NousDatabase): TaskDao =
		nousDatabase.taskDao()

	@Singleton
	@Provides
	fun provideJournalDao(nousDatabase: NousDatabase): JournalDao =
		nousDatabase.journalDao()
}
