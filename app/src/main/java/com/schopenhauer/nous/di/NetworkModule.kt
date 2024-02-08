package com.schopenhauer.nous.di

import com.schopenhauer.nous.BuildConfig
import com.schopenhauer.nous.data.remote.APP_BASE_URL
import com.schopenhauer.nous.data.remote.CONNECT_TIMEOUT_SECONDS
import com.schopenhauer.nous.data.remote.OTHER_BASE_URL
import com.schopenhauer.nous.data.remote.READ_TIMEOUT_SECONDS
import com.schopenhauer.nous.data.remote.WRITE_TIMEOUT_SECONDS
import com.schopenhauer.nous.data.remote.api.AppApi
import com.schopenhauer.nous.data.remote.datasource.AppRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .addInterceptor(provideHttpLoggingInterceptor())
      .build()
  }

  private fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
    .apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }

  @Provides
  @Singleton
  fun provideAppRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val newClient = okHttpClient.newBuilder()
      .addNetworkInterceptor(provideAppNetworkInterceptor())
      .build()

    return Retrofit.Builder()
      .baseUrl(APP_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(newClient)
      .build()
  }

  private fun provideAppNetworkInterceptor() = Interceptor {
    val originRequest = it.request()
    val newRequest = originRequest.newBuilder()
      .addHeader("Authorization", "")
      .method(originRequest.method, originRequest.body)
      .build()
    val response = it.proceed(newRequest)

    response
  }

  @Provides
  @Singleton
  fun provideOtherRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val newClient = okHttpClient.newBuilder()
      .addNetworkInterceptor(provideOtherNetworkInterceptor())
      .build()

    return Retrofit.Builder()
      .baseUrl(OTHER_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(newClient)
      .build()
  }

  private fun provideOtherNetworkInterceptor() = Interceptor {
    val originRequest = it.request()
    val newRequest = originRequest.newBuilder()
      .addHeader("Authorization", "")
      .method(originRequest.method, originRequest.body)
      .build()
    val response = it.proceed(newRequest)

    response
  }

  @Provides
  @Singleton
  fun provideAppApi(retrofit: Retrofit): AppApi {
    return retrofit.create(AppApi::class.java)
  }

  @Provides
  @Singleton
  fun provideAppRemoteDataSource(appApi: AppApi): AppRemoteDataSource {
    return AppRemoteDataSource(appApi)
  }
}
