package com.example.githubproject.di

import android.content.Context
import androidx.room.Room
import com.example.githubproject.data.local.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideGithubDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GithubDatabase::class.java, "github_database")
            .fallbackToDestructiveMigration().build()
}