package com.example.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.core.hide
import com.example.blogapp.core.show
import com.example.blogapp.data.remote.auth.LoginDataSource
import com.example.blogapp.databinding.FragmentRegisterBinding
import com.example.blogapp.domain.auth.AuthRepoImpl
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.presentation.auth.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(LoginDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPass.text.toString().trim()

            if (validUserData(username, password, confirmPassword, email)) return@setOnClickListener

            createUser(email, password, username)

        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignUp.isEnabled = false
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
                }
            }
        })
    }

    private fun validUserData(
        username: String,
        password: String,
        confirmPassword: String,
        email: String
    ): Boolean {
        when {
            username.isEmpty() -> {
                binding.etUsername.error = "username is empty"
                return true
            }
            password != confirmPassword -> {
                binding.etConfirmPass.error = "password does not match"
                binding.etPassword.error = "password does not match"
                return true
            }
            email.isEmpty() -> {
                binding.etEmail.error = "email is empty"
                return true
            }
            password.isEmpty() -> {
                binding.etPassword.error = "password is empty"
                return true
            }
            confirmPassword.isEmpty() -> {
                binding.etConfirmPass.error = "password is empty"
                return true
            }
        }
        return false
    }
}