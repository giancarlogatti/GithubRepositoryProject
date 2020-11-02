package com.example.githubproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubproject.R
import com.example.githubproject.data.remote.GithubRepo
import de.hdodenhof.circleimageview.CircleImageView

class GithubRepoListAdapter(private val repos: List<GithubRepo>,
                            private val githubRepoListener: GithubRepoListener? = null)
                            : RecyclerView.Adapter<GithubRepoListAdapter.ViewHolder>() {

    interface GithubRepoListener {
        fun onGithubRepoFavorited(githubRepo: GithubRepo)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgAvatar = view.findViewById<CircleImageView>(R.id.img_avatar)
        val tvRepoName = view.findViewById<TextView>(R.id.tv_repository_name)
        val tvRepoDescription = view.findViewById<TextView>(R.id.tv_repository_description)
        val imgFavorite = view.findViewById<ImageView>(R.id.img_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.github_repo_layout, parent, false))

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repos[position]
        Glide
            .with(holder.itemView.context)
            .load(repository.owner.avatarUrl)
            .into(holder.imgAvatar)
        holder.tvRepoName.text = repository.name
        holder.tvRepoDescription.text = repository.description
        repository.isFavorited?.let { favorited->
            if (favorited) {
                //needs to be filled heart icon
                holder.imgFavorite.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.ic_baseline_favorite_filled_24))
            }
        }
    }
}