package com.example.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repo: HomeScreenRepo): ViewModel() {
    fun fetchLatestPost() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getLastestPost())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    val lastestPosts: StateFlow<Result<List<Post>>> = flow {
        runCatching {
            repo.getLastestPost()
        }.onSuccess {
            emit(it)
        }.onFailure { throwable ->
            emit(Result.Failure(Exception(throwable)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )

    private val posts = MutableStateFlow<Result<List<Post>>>(Result.Loading())
    fun fetchPost() = viewModelScope.launch {
        runCatching {
            repo.getLastestPost()
        }.onSuccess {
            posts.value = it
        }.onFailure { throwable ->
            posts.value = Result.Failure(Exception(throwable))
        }
    }
    fun getPosts(): StateFlow<Result<List<Post>>> = posts
}


class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}