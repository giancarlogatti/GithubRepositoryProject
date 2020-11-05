package com.example.githubproject.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubproject.data.GithubRepoRepository
import com.example.githubproject.data.local.FavoritedGithubRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class FavoritedGithubRepoViewModel @ViewModelInject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {

    companion object {
        const val FAVORITED_REPO_LIST = "favoritedRepoList"
    }
    private var disposable: Disposable? = null
    private val _favoritedReposLiveData =
        savedStateHandle.getLiveData<List<FavoritedGithubRepo>>(FAVORITED_REPO_LIST)
    val favoritedReposLiveData
        get() = _favoritedReposLiveData

    init {
        disposable = githubRepoRepository.getAllFavoritedRepos()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { repos->
                _favoritedReposLiveData.value = repos
            }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}