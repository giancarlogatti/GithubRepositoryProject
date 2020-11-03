package com.example.githubproject.di

import com.example.githubproject.data.remote.GithubRepoApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideOkhttpClient() = OkHttpClient.Builder()
        .retryOnConnectionFailure(false)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "").build()
            it.proceed(request)
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideGithubService(retrofit: Retrofit) = retrofit.create(GithubRepoApi::class.java)
}