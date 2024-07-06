package com.training.codespire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.training.codespire.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)



        navToRegister()


        checkLogin()



        return binding.root
    }

    private fun navToRegister() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun checkLogin() {
        binding.btnSignIn.setOnClickListener {
            validateField(binding.etEmail, R.drawable.ic_message_selector, R.drawable.ic_error_message, "Email is required")
            validateField(binding.etPassword, R.drawable.ic_password_selector, R.drawable.ic_error_password, "Password is required")
        }
    }

    private fun validateField(field: EditText, normalIcon: Int, errorIcon: Int, errorMessage: String) {
        if (field.text.isEmpty()) {
            setFieldError(field, errorIcon, errorMessage)
        } else {
            setFieldNormal(field, normalIcon)
        }
    }

    private fun setFieldError(field: EditText, errorIcon: Int, errorMessage: String) {
        field.error = errorMessage
        field.setBackgroundResource(R.drawable.et_border_error)
        field.setCompoundDrawablesWithIntrinsicBounds(errorIcon, 0, 0, 0)
    }

    private fun setFieldNormal(field: EditText, normalIcon: Int) {
        field.setBackgroundResource(R.drawable.et_border_selector)
        field.setCompoundDrawablesWithIntrinsicBounds(normalIcon, 0, 0, 0)
    }

}



