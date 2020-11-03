package com.example.githubproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGithubRepo(githubRepo: FavoritedGithubRepo)

    @Query("SELECT EXISTS(SELECT * FROM Repositories WHERE id = :id)")
    fun isRepoFavorited(id: Long): Single<Boolean>
}