package com.schopenhauer.nous.di

import android.content.Context
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.datasource.NousLocalDataSource
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
	fun provideNous(@ApplicationContext context: Context): NousLocalDataSource =
		NousLocalDataSource.buildDatabase(context)

	@Singleton
	@Provides
	fun provideTaskDao(nousLocalDataSource: NousLocalDataSource): TaskDao =
		nousLocalDataSource.taskDao()
}