package com.example.githubproject.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubproject.R
import com.example.githubproject.data.remote.GithubRepo
import com.example.githubproject.databinding.ActivityGithubRepoBinding
import com.example.githubproject.databinding.FragmentGithubRepoBinding
import com.example.githubproject.ui.GithubRepoViewModel
import com.example.githubproject.ui.adapter.GithubRepoListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubRepoFragment : Fragment(R.layout.fragment_github_repo), GithubRepoListAdapter.GithubRepoListener {

    private var _binding: FragmentGithubRepoBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var githubRepoListAdapter: GithubRepoListAdapter
    private val githubRepoViewModel: GithubRepoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGithubRepoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        githubRepoViewModel.queriedReposLiveData.observe(viewLifecycleOwner){
            githubRepoListAdapter.submitRepoData(it)
        }
    }

    //save this github repository as a favorited repo
    override fun onGithubRepoFavorited(githubRepo: GithubRepo) {
        githubRepoViewModel.saveFavoritedGithubRepo(githubRepo.toFavoritedGithubRepo())
        Snackbar.make(binding.root, "${githubRepo.name} has been favorited!", Snackbar.LENGTH_SHORT).show()
    }
    //remove from database if unfavorited
    override fun onGithubRepoUnFavorited(githubRepo: GithubRepo) {
        githubRepoViewModel.unfavoriteGithubRepo(githubRepo.toFavoritedGithubRepo())
        Snackbar.make(binding.root, "${githubRepo.name} has been unfavorited!", Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        githubRepoListAdapter = GithubRepoListAdapter(this)
        binding.rvRepositories.apply{
            adapter = githubRepoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}