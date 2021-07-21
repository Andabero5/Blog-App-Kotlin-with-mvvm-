package com.example.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.blogapp.R
import com.example.blogapp.ui.home.adapter.HomeScreenAdapter
import com.example.blogapp.data.remote.home.HomeScreenDataSource
import com.example.blogapp.databinding.FragmentHomeScreenBinding
import com.example.blogapp.domain.home.HomeScreenRepoImplements
import com.example.blogapp.presentation.HomeScreenViewModel
import com.example.blogapp.presentation.HomeScreenViewModelFactory
import com.example.blogapp.core.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    private val viewModel by viewModels<HomeScreenViewModel>{HomeScreenViewModelFactory(
        HomeScreenRepoImplements(HomeScreenDataSource())
    )}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.lastestPosts.collect { result->
                    when (result){
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success ->{
                            binding.progressBar.visibility = View.GONE
                            if (result.data.isEmpty()) {
                                binding.emptyContainer.show()
                                return@collect
                            }else{
                                binding.emptyContainer.hide()
                            }
                            binding.rvHome.adapter = HomeScreenAdapter(result.data)
                        }
                        is Result.Failure ->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Ocurrio un error ${result.exception}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}