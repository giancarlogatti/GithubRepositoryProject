package com.example.githubproject.ui

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubproject.data.GithubRepoRepository
import com.example.githubproject.data.local.FavoritedGithubRepo
import com.example.githubproject.data.remote.GithubRepo
import com.example.githubproject.ui.util.toLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class GithubRepoViewModel @ViewModelInject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {

    companion object {
        const val QUERIED_REPO_LIST = "queriedRepoList"
        const val FAVORITED_REPO_LIST = "favoritedRepoList"
    }

    private var disposable: Disposable? = null

    private val _queriedReposLiveData =
        savedStateHandle.getLiveData<List<GithubRepo>>(QUERIED_REPO_LIST)
    val queriedReposLiveData
        get() = _queriedReposLiveData

    private val _favoritedReposLiveData =
        savedStateHandle.getLiveData<List<FavoritedGithubRepo>>(FAVORITED_REPO_LIST)
    val favoritedReposLiveData
        get() = _favoritedReposLiveData

    fun fetchRemoteGithubRepos(query: String) {
        disposable = githubRepoRepository.fetchRemoteGithubRepos(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { repos->
                Log.i("fetchGithubRepos", "hello")
                _queriedReposLiveData.value = repos
            }
    }

    fun fetchFavoritedGithubRepos() {
        _favoritedReposLiveData.value = githubRepoRepository.getAllFavoritedRepos().toLiveData().value
    }

    fun saveFavoritedGithubRepo(githubRepo: FavoritedGithubRepo){
        val d = githubRepoRepository.saveFavoritedGithubRepo(githubRepo)
    }

    fun unfavoriteGithubRepo(githubRepo: FavoritedGithubRepo) {
        val x = githubRepoRepository.unfavoriteGithubRepo(githubRepo)
    }
}