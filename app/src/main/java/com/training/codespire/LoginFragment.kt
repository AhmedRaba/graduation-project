package com.training.codespire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.training.codespire.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)





        checkLogin()




        return binding.root
    }

    private fun checkLogin() {
        binding.btnSignIn.setOnClickListener {
            if (binding.etEmail.text.isEmpty()) {
                binding.etEmail.error = "Email is required"
                binding.etEmail.setBackgroundResource(R.drawable.et_border_error)
                binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_error_message, 0, 0, 0
                )
            } else
                binding.etEmail.setBackgroundResource(R.drawable.et_border_selector)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_selector, 0, 0, 0
            )
        }
        if (binding.etPassword.text.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.setBackgroundResource(R.drawable.et_border_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_error_password, 0, 0, 0
            )
        } else {
            binding.etPassword.setBackgroundResource(R.drawable.et_border_selector)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_selector, 0, 0, 0
            )
        }
    }


}