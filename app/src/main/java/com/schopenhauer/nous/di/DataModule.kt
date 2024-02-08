package com.schopenhauer.nous.di

import com.schopenhauer.nous.data.repository.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
  @Binds
  fun bindsAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepositoryImpl
}
