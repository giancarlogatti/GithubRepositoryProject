package com.example.githubproject.data.remote

import com.squareup.moshi.Json

data class GithubRepos (
    @Json(name = "items") val repos: List<GithubRepo>
)

data class GithubRepo (
    @Json(name = "id") val id: Long,
    @Json(name = "full_name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "language") val language: String,
    @Json(name = "owner") val owner: Owner,
    //fetch a list of the IDs of repositories favorited locally, and if IDs match, mark isFavorited as True
    val isFavorited: Boolean? = null
)

data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String
)