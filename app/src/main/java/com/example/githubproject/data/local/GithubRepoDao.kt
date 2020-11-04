package com.example.githubproject.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteGithubRepo(githubRepo: FavoritedGithubRepo): Completable

    @Delete
    fun unfavoriteGithubRepo(githubRepo: FavoritedGithubRepo): Completable

    @Query("SELECT EXISTS(SELECT * FROM Repositories WHERE id = :id)")
    fun isRepoFavorited(id: Long): Single<Boolean>

    @Query("SELECT * FROM Repositories")
    fun getAllFavoritedRepositories(): Single<List<FavoritedGithubRepo>>
}