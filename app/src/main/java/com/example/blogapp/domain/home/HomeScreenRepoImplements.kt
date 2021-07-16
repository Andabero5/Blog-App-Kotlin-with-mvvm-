package com.example.blogapp.domain.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImplements(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLastestPost(): Result<List<Post>> = dataSource.getLastestPost()
}