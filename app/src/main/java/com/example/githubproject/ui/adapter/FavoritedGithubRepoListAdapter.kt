package com.example.githubproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubproject.R
import com.example.githubproject.data.local.FavoritedGithubRepo
import de.hdodenhof.circleimageview.CircleImageView

class FavoritedGithubRepoListAdapter(private val repos: List<FavoritedGithubRepo>): RecyclerView.Adapter<FavoritedGithubRepoListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgAvatar = view.findViewById<CircleImageView>(R.id.img_avatar)
        val tvRepoName = view.findViewById<TextView>(R.id.tv_repository_name)
        val tvRepoDescription = view.findViewById<TextView>(R.id.tv_repository_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.github_repo_layout, parent, false))

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repos[position]
        Glide
            .with(holder.itemView.context)
            .load(repository.avatarUrl)
            .into(holder.imgAvatar)
        holder.tvRepoName.text = repository.name
        holder.tvRepoDescription.text = repository.description
    }
}