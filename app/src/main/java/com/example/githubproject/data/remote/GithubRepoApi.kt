package com.example.githubproject.data.remote

import retrofit2.http.GET

interface GithubRepoApi {

    @GET
    fun getGithubRepositories(){

    }
}