package com.example.blogapp.UI.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.blogapp.R
import com.example.blogapp.UI.home.adapter.HomeScreenAdapter
import com.example.blogapp.data.model.Post
import com.example.blogapp.databinding.FragmentHomeScreenBinding
import com.google.firebase.Timestamp


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        val postList = listOf(Post("https://www.welivesecurity.com/wp-content/uploads/es-la/2012/12/Logo-Android.png", "andres", Timestamp.now(), "https://inmediatum.com/wp-content/uploads/2020/12/mvvm.jpg"),
            Post("https://www.welivesecurity.com/wp-content/uploads/es-la/2012/12/Logo-Android.png", "andres", Timestamp.now(), "https://inmediatum.com/wp-content/uploads/2020/12/mvvm.jpg"))
        binding.rvHome.adapter = HomeScreenAdapter(postList)
    }
}