package com.example.blogapp.domain

import com.example.blogapp.core.Resource
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.HomeScreenDataSource

class HomeScreenRepoImplements(private val dataSource: HomeScreenDataSource):HomeScreenRepo {
    override suspend fun getLastestPost(): Resource<List<Post>> = dataSource.getLastestPost()
}