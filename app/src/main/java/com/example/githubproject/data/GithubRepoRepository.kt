package com.example.githubproject.data

import com.example.githubproject.data.local.GithubDatabase
import com.example.githubproject.data.remote.GithubRepo
import com.example.githubproject.data.remote.GithubRepoApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(private val githubRepoApi: GithubRepoApi, private val githubDatabase: GithubDatabase) {

    fun fetchRemoteGithubRepos(query: String): Single<List<GithubRepo>> {
        return githubRepoApi.getGithubRepositories(query)
            .subscribeOn(Schedulers.io())
            //map from GithubRepos to list of GithubRepo
            .map {
                it.repos
            }
            //returns an Observable based on the List of Repos
            .flatMapObservable {
                Observable.fromIterable(it)
                    .subscribeOn(Schedulers.io())
            }
            //maps each GithubRepo emitted by the Observable to a Single, and the operator
            //will merge them all into the Observable
            .flatMapSingle {
                isGithubRepoFavorited(it)
            }
            //gets emissions from Observable and converts it into a list that emits once (Single)
            .toList()
    }

    fun isGithubRepoFavorited(repo: GithubRepo): Single<GithubRepo> {
        return githubDatabase.githubRepoDao().isRepoFavorited(repo.id)
            .subscribeOn(Schedulers.io())
            .map { favorited->
                repo.apply {
                    isFavorited = favorited
                }
            }
    }
}