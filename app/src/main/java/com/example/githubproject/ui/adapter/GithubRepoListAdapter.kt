package com.example.githubproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubproject.R
import com.example.githubproject.data.remote.GithubRepo
import de.hdodenhof.circleimageview.CircleImageView

class GithubRepoListAdapter(private val githubRepoListener: GithubRepoListener? = null)
                            : RecyclerView.Adapter<GithubRepoListAdapter.ViewHolder>() {

    private var repos = listOf<GithubRepo>()

    interface GithubRepoListener {
        fun onGithubRepoFavorited(githubRepo: GithubRepo)
        fun onGithubRepoUnFavorited(githubRepo: GithubRepo)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgAvatar = view.findViewById<CircleImageView>(R.id.img_avatar)
        val tvRepoName = view.findViewById<TextView>(R.id.tv_repository_name)
        val tvRepoDescription = view.findViewById<TextView>(R.id.tv_repository_description)
        val imgFavorite = view.findViewById<ImageView>(R.id.img_favorite)
    }

    fun submitRepoData(newRepos: List<GithubRepo>){
        val diffResult = DiffUtil.calculateDiff(
            RepoDiffCallback(
                repos,
                newRepos
            )
        )
        repos = newRepos
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.github_repo_layout, parent, false))

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repos[position]
        val filledHeart = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_baseline_favorite_filled_24)
        val unfilledHeart = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_baseline_favorite_border_24)
        Glide
            .with(holder.itemView.context)
            .load(repository.owner.avatarUrl)
            .into(holder.imgAvatar)
        holder.tvRepoName.text = repository.name
        holder.tvRepoDescription.text = repository.description
        repository.isFavorited?.let { isFavorited->
            if (isFavorited) {
                holder.imgFavorite.setImageDrawable(filledHeart)
            } else {
                holder.imgFavorite.setImageDrawable(unfilledHeart)
            }
            holder.imgFavorite.setOnClickListener {
                repository.isFavorited?.let { repoIsFavorited->
                    if(repoIsFavorited) {
                        repository.isFavorited = false
                        holder.imgFavorite.setImageDrawable(unfilledHeart)
                        githubRepoListener?.onGithubRepoUnFavorited(repository)
                    } else {
                        repository.isFavorited = true
                        holder.imgFavorite.setImageDrawable(filledHeart)
                        githubRepoListener?.onGithubRepoFavorited(repository)
                    }
                }
            }
        }
    }

    private class RepoDiffCallback(private val oldList: List<GithubRepo>, private val newList: List<GithubRepo>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}