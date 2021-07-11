package com.example.blogapp.data.model

import java.security.Timestamp

data class Post(
    val profilePicture: String = "",
    val profileName: String = "",
    val postTimeStamp: Timestamp? = null,
    val postImage: String = ""
)
