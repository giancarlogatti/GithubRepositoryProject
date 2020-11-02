package com.example.githubproject.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object GithubRepoService {

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor {
                var original = it.request()
                val url = original.url().newBuilder()
                    .addQueryParameter("repositories", "retrofit")
                    .addQueryParameter("api_key", "").build()
                original = original.newBuilder().url(url).build()
                it.proceed(original)
            }.build()

    private const val githubBaseUrl = "https://api.github.com/search/"

    private val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(githubBaseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}