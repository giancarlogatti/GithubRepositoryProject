package com.example.githubproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubproject.R
import com.example.githubproject.data.local.FavoritedGithubRepo
import de.hdodenhof.circleimageview.CircleImageView

class FavoritedGithubRepoListAdapter : RecyclerView.Adapter<FavoritedGithubRepoListAdapter.ViewHolder>() {

    private var repos = listOf<FavoritedGithubRepo>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgAvatar = view.findViewById<CircleImageView>(R.id.img_avatar)
        val tvRepoName = view.findViewById<TextView>(R.id.tv_repository_name)
        val tvRepoDescription = view.findViewById<TextView>(R.id.tv_repository_description)
        val tvRepoLanguage = view.findViewById<TextView>(R.id.tv_repository_language)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorited_github_repo_layout, parent, false))

    fun submitFavoritedMovies(newRepos: List<FavoritedGithubRepo>) {
        val diffResult = DiffUtil.calculateDiff(
            FavoritedRepoDiffCallback(
                repos,
                newRepos
            )
        )
        repos = newRepos
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repos[position]
        Glide
            .with(holder.itemView.context)
            .load(repository.avatarUrl)
            .into(holder.imgAvatar)
        holder.tvRepoName.text = repository.name
        holder.tvRepoDescription.text = repository.description
        holder.tvRepoLanguage.text = repository.language
    }

    private class FavoritedRepoDiffCallback(
        private val oldList: List<FavoritedGithubRepo>, private val newList: List<FavoritedGithubRepo>): DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                newList[newItemPosition].id == oldList[oldItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                newList[newItemPosition] == oldList[oldItemPosition]
    }
}
