package com.example.githubproject.ui.screens

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubproject.R
import com.example.githubproject.data.remote.GithubRepo
import com.example.githubproject.databinding.ActivityGithubRepoBinding
import com.example.githubproject.ui.GithubRepoViewModel
import com.example.githubproject.ui.adapter.GithubRepoListAdapter

class GithubRepoActivity : AppCompatActivity(), MenuItem.OnMenuItemClickListener, GithubRepoListAdapter.GithubRepoListener {

    private lateinit var binding: ActivityGithubRepoBinding
    private lateinit var githubRepoListAdapter: GithubRepoListAdapter
    private val githubRepoViewModel: GithubRepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    //save this github repository as a favorited repo
    override fun onGithubRepoFavorited(githubRepo: GithubRepo) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        item?.let {
            if(it.itemId == R.menu.overflow_menu) {
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragment_container, FavoriteRepoFragment()).commit()
            }
        }
        return true
    }

    fun initRecyclerView() {
        binding.rvRepositories.apply{
            adapter = githubRepoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}