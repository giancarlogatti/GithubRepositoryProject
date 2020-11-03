package com.example.githubproject.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepoApi {

    @GET("/search/repositories")
    fun getGithubRepositories(@Query("q") searchValue: String): Single<GithubRepos>
}