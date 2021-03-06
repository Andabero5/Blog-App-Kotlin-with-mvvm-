package com.example.blogapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blogapp.core.BaseViewHolder
import com.example.blogapp.core.TimeAgo
import com.example.blogapp.core.hide
import com.example.blogapp.data.model.Post
import com.example.blogapp.databinding.PostItemViewBinding

class HomeScreenAdapter(private val postList: List<Post>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            Glide.with(context).load(item.postImage).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profilePicture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profileName
            val createdAt = (item.createdAt?.time?.div(1000L))?.let{
                TimeAgo.getTimeAgo(it.toInt())
            }
            binding.postTimeStamp.text = createdAt
            if (item.postDescription.isEmpty()) {
                binding.postDescription.hide()
            } else {
                binding.postDescription.text = item.postDescription
            }
        }
    }
}