package com.schopenhauer.nous.di

import com.schopenhauer.nous.BuildConfig
import com.schopenhauer.nous.data.network.CONNECT_TIMEOUT_SECONDS
import com.schopenhauer.nous.data.network.NetworkOwner
import com.schopenhauer.nous.data.network.READ_TIMEOUT_SECONDS
import com.schopenhauer.nous.data.network.WRITE_TIMEOUT_SECONDS
import com.schopenhauer.nous.data.network.api.NaverSearchApi
import com.schopenhauer.nous.data.network.api.X_NAVER_CLIENT_ID_HEADER
import com.schopenhauer.nous.data.network.api.X_NAVER_CLIENT_SECRET_HEADER
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
  fun provideNaverSearchRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val newClient = okHttpClient.newBuilder()
      .addNetworkInterceptor(provideNaverSearchNetworkInterceptor())
      .build()

    return Retrofit.Builder()
      .baseUrl(NetworkOwner.NAVER.baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .client(newClient)
      .build()
  }

  private fun provideNaverSearchNetworkInterceptor() = Interceptor {
    val originRequest = it.request()
    val newRequest = originRequest.newBuilder()
      .addHeader(X_NAVER_CLIENT_ID_HEADER, BuildConfig.NAVER_CLIENT_ID)
      .addHeader(X_NAVER_CLIENT_SECRET_HEADER, BuildConfig.NAVER_CLIENT_SECRET)
      .method(originRequest.method, originRequest.body)
      .build()
    val response = it.proceed(newRequest)

    response
  }

  @Provides
  @Singleton
  fun provideNaverSearchApi(retrofit: Retrofit): NaverSearchApi {
    return retrofit.create(NaverSearchApi::class.java)
  }
}
