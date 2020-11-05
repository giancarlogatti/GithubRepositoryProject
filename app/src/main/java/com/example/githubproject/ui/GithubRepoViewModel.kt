package com.example.githubproject.ui

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubproject.data.repository.GithubRepoRepository
import com.example.githubproject.data.local.FavoritedGithubRepo
import com.example.githubproject.data.remote.GithubRepos
import com.example.githubproject.util.Either
import com.example.githubproject.util.Failure
import com.example.githubproject.util.LoadingState
import com.example.githubproject.util.ServerException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class GithubRepoViewModel @ViewModelInject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {

    companion object {
        const val QUERIED_REPO_LIST = "queriedRepoList"
        const val SEARCH_QUERY = "searchQuery"
    }
    private var reposDisposable: Disposable? = null
    private var compositeDisposable = CompositeDisposable()

    var currentSearchedQuery = savedStateHandle.get<String>(SEARCH_QUERY)

    private val _queryLoadingStateLiveData: MutableLiveData<LoadingState> = MutableLiveData()
    val queryLoadingStateLiveData: LiveData<LoadingState>
        get() = _queryLoadingStateLiveData

    private val _queriedReposLiveData =
        savedStateHandle.getLiveData<Either<Failure, GithubRepos>>(QUERIED_REPO_LIST)
    val queriedReposLiveData: LiveData<Either<Failure, GithubRepos>>
        get() = _queriedReposLiveData

    fun fetchRemoteGithubRepos(query: String) {
        //change the current searched query and also saved it in SaveStateHandle
        currentSearchedQuery = query
        savedStateHandle.set(SEARCH_QUERY, currentSearchedQuery)

        //checks if query loading state is already loading (user might enter new
        // query before any response is reached)
        if(queryLoadingStateLiveData.value == LoadingState.LOADING) {
            //this will propogate cancellation of any previous network call
            reposDisposable?.dispose()
        }
        _queryLoadingStateLiveData.value = LoadingState.LOADING
        reposDisposable = githubRepoRepository.fetchRemoteGithubRepos(query)
            .onErrorReturn {
                if(it is ServerException) {
                    Either.Left<Failure.ServerFailure, GithubRepos>(Failure.ServerFailure(it.responseCode))
                } else
                    Either.Left<Failure.ConnectionFailure, GithubRepos>(Failure.ConnectionFailure)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response-> //may either be an Either.Left which is a failure or Either.Right which is a success
                _queriedReposLiveData.value = response
                _queryLoadingStateLiveData.value = LoadingState.LOADED
            }
        reposDisposable?.let { compositeDisposable.add(it) }
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