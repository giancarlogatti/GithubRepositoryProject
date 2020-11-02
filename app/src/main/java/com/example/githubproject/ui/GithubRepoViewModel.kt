package com.example.githubproject.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubproject.data.GithubRepoRepository
import com.example.githubproject.data.local.FavoritedGithubRepo

class GithubRepoViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val githubRepoRepository = GithubRepoRepository()

    fun fetchRemoteGithubRepos(){

    }

    fun saveGithubRepo(githubRepo: FavoritedGithubRepo){

    }

    fun fetchLocalGithubRepos(){

    }
}