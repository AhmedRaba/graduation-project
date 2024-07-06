package com.training.codespire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.codespire.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)



        navigateToLogin()


        checkRegister()



        return binding.root
    }

    private fun navigateToLogin() {
        binding.tvRegSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }


    private fun checkRegister() {
        binding.btnSingUp.setOnClickListener {
            validateField(binding.etRegUsername, R.drawable.ic_user_selector, R.drawable.ic_error_user, "Username is required")
            validateField(binding.etRegEmail, R.drawable.ic_message_selector, R.drawable.ic_error_message, "Email is required")
            validateField(binding.etRegPassword, R.drawable.ic_password_selector, R.drawable.ic_error_password, "Password is required")

            val password = binding.etRegPassword.text.toString()
            val confirmPassword = binding.etRegConfirmPassword.text.toString()

            if (confirmPassword.isEmpty()) {
                setFieldError(binding.etRegConfirmPassword, R.drawable.ic_password_selector, R.drawable.ic_error_password, "Password is required")
            } else if (password != confirmPassword) {
                setFieldError(binding.etRegConfirmPassword, R.drawable.ic_password_selector, R.drawable.ic_error_password, "Password doesn't match")
            } else {
                setFieldNormal(binding.etRegConfirmPassword, R.drawable.ic_password_selector)
            }
        }
    }

    private fun validateField(field: EditText, normalIcon: Int, errorIcon: Int, errorMessage: String) {
        if (field.text.isEmpty()) {
            setFieldError(field, normalIcon, errorIcon, errorMessage)
        } else {
            setFieldNormal(field, normalIcon)
        }
    }

    private fun setFieldError(field: EditText, normalIcon: Int, errorIcon: Int, errorMessage: String) {
        field.error = errorMessage
        field.setBackgroundResource(R.drawable.et_border_error)
        field.setCompoundDrawablesWithIntrinsicBounds(errorIcon, 0, 0, 0)
    }

    private fun setFieldNormal(field: EditText, normalIcon: Int) {
        field.setBackgroundResource(R.drawable.et_border_selector)
        field.setCompoundDrawablesWithIntrinsicBounds(normalIcon, 0, 0, 0)
    }


}