package com.example.githubproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritedGithubRepo::class], version = 1, exportSchema = false)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun githubRepoDao(): GithubRepoDao
}