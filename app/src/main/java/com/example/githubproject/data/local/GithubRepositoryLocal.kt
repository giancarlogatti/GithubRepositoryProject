package com.example.githubproject.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GithubRepositoryLocal(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String
)