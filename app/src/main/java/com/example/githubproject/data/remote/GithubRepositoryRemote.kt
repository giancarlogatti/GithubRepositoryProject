package com.example.githubproject.data.remote

import com.squareup.moshi.Json

data class GithubRepositoryRemote (
    @Json(name = "full_name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "language") val language: String,
    @Json(name = "owner") val owner: Owner
)

data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String
)