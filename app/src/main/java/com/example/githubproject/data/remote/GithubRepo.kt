package com.example.githubproject.data.remote

import android.os.Parcelable
import com.example.githubproject.data.local.FavoritedGithubRepo
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepos (
    @Json(name = "items") val repos: List<GithubRepo>
): Parcelable

@Parcelize
data class GithubRepo (
    @Json(name = "id") val id: Long,
    @Json(name = "full_name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "language") val language: String?,
    @Json(name = "owner") val owner: Owner,
    //fetch a list of the IDs of repositories favorited locally, and if IDs match, mark isFavorited as True
    var isFavorited: Boolean? = null
): Parcelable {
    fun toFavoritedGithubRepo() = FavoritedGithubRepo(
        id,
        name,
        description,
        language,
        owner.avatarUrl
    )
}

@Parcelize
data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String
): Parcelable