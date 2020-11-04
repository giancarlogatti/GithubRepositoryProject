package com.example.githubproject.ui.screens

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubproject.R
import com.example.githubproject.data.remote.GithubRepo
import com.example.githubproject.databinding.ActivityGithubRepoBinding
import com.example.githubproject.ui.GithubRepoViewModel
import com.example.githubproject.ui.adapter.GithubRepoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubRepoActivity : AppCompatActivity(), GithubRepoListAdapter.GithubRepoListener {

    private lateinit var binding: ActivityGithubRepoBinding
    private lateinit var githubRepoListAdapter: GithubRepoListAdapter
    private val githubRepoViewModel: GithubRepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setOnMenuItemClickListener{ item->
            item?.let {
                if(it.itemId == R.menu.overflow_menu) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, FavoriteRepoFragment()).commit()
                }
            }
            true
        }
        initRecyclerView()
        binding.searchView.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        githubRepoViewModel.fetchRemoteGithubRepos(it)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean { return true }
            }
        )
        githubRepoViewModel.queriedReposLiveData.observe(this){
            githubRepoListAdapter.submitRepoData(it)
        }
    }

    //save this github repository as a favorited repo
    override fun onGithubRepoFavorited(githubRepo: GithubRepo) {
        githubRepoViewModel.saveFavoritedGithubRepo(githubRepo.toFavoritedGithubRepo())
    }
    //remove from database if unfavorited
    override fun onGithubRepoUnFavorited(githubRepo: GithubRepo) {
        githubRepoViewModel.unfavoriteGithubRepo(githubRepo.toFavoritedGithubRepo())
    }

    private fun initRecyclerView() {
        githubRepoListAdapter = GithubRepoListAdapter(this)
        binding.rvRepositories.apply{
            adapter = githubRepoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}