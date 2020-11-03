package com.example.githubproject.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubproject.R
import com.example.githubproject.databinding.FragmentFavoriteRepoBinding
import com.example.githubproject.ui.GithubRepoViewModel
import com.example.githubproject.ui.adapter.FavoritedGithubRepoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRepoFragment : Fragment(R.layout.fragment_favorite_repo) {

    private var _binding: FragmentFavoriteRepoBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var favoritedGithubRepoListAdapter: FavoritedGithubRepoListAdapter
    private val githubRepoViewModel: GithubRepoViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        githubRepoViewModel.favoritedReposLiveData.observe(viewLifecycleOwner){
            favoritedGithubRepoListAdapter.submitFavoritedMovies(it)
        }
    }

    private fun initRecyclerView() {
        binding.rvFavoritedRepos.apply {
            adapter = favoritedGithubRepoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}