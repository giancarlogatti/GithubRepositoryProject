package com.example.githubproject.ui

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubproject.data.GithubRepoRepository
import com.example.githubproject.data.local.FavoritedGithubRepo
import com.example.githubproject.data.remote.GithubRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class GithubRepoViewModel @ViewModelInject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {

    companion object {
        const val QUERIED_REPO_LIST = "queriedRepoList"
    }

    private var compositeDisposable = CompositeDisposable()

    private val _queriedReposLiveData =
        savedStateHandle.getLiveData<List<GithubRepo>>(QUERIED_REPO_LIST)
    val queriedReposLiveData
        get() = _queriedReposLiveData

    fun fetchRemoteGithubRepos(query: String) {
        val disposable = githubRepoRepository.fetchRemoteGithubRepos(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { repos->
                _queriedReposLiveData.value = repos
            }
        compositeDisposable.add(disposable)
    }

    fun saveFavoritedGithubRepo(githubRepo: FavoritedGithubRepo){
        val disposable = githubRepoRepository.saveFavoritedGithubRepo(githubRepo)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.i("GithubRepoViewModel", "error occurred saving repo")
            }
            .subscribe {
                Log.i("GithubRepoViewModel", "successfully saved repo!")
            }
        compositeDisposable.add(disposable)
    }

    fun unfavoriteGithubRepo(githubRepo: FavoritedGithubRepo) {
        val disposable = githubRepoRepository.unfavoriteGithubRepo(githubRepo)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.i("GithubRepoViewModel", "error occurred deleting repo")
            }
            .subscribe {
                Log.i("GithubRepoViewModel", "successfully removed repo")
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}