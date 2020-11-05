package com.example.githubproject.di

import com.example.githubproject.data.remote.GithubRepoApi
import com.example.githubproject.util.GITHUB_API_TOKEN
import com.example.githubproject.util.NoConnectivityException
import com.example.githubproject.util.Variables
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
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor {
            if(!Variables.isNetworkActive) {
                throw NoConnectivityException()
            }
            val request = it.request().newBuilder()
                .addHeader("Authorization", "token $GITHUB_API_TOKEN").build()
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