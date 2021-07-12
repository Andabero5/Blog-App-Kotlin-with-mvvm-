package com.example.blogapp.domain.home

import com.example.blogapp.core.Resource
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImplements(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLastestPost(): Resource<List<Post>> = dataSource.getLastestPost()
}