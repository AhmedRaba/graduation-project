package com.training.codespire.ui.frags

import com.training.codespire.MainActivity
import com.training.codespire.data.datastore.SharedPreferencesUtil
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.training.codespire.R
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private lateinit var authViewmodel: AuthViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())

        authViewmodel = ViewModelProvider(this)[AuthViewmodel::class.java]

        authViewmodel.loginResponseLiveData.observe(viewLifecycleOwner) { loginResponse ->
            showLoading()
            loginResponse?.let {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                sharedPreferencesUtil.isLoggedIn = true
                navigateToMainActivity()
            }
        }

        authViewmodel.errorLiveData.observe(viewLifecycleOwner) { error ->
            error?.let {
                hideLoading()
                showError()
            }
        }

        navToRegister()
        checkLogin()

        return binding.root
    }

    private fun navToRegister() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkLogin() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(email, password)) {
                authViewmodel.loginUser(email, password)
            }
        }

        setupPasswordVisibilityToggle(binding.etPassword, R.drawable.ic_eye, R.drawable.ic_eye_off)
    }

    private fun navigateToMainActivity() {
        showLoading()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            setFieldError(binding.etEmail, binding.tvEmailError, "Email is required")
            isValid = false
        } else {
            setFieldNormal(binding.etEmail, binding.tvEmailError)
        }

        if (password.isEmpty()) {
            setFieldError(binding.etPassword, binding.tvPasswordError, "Password is required")
            isValid = false
        } else {
            setFieldNormal(binding.etPassword, binding.tvPasswordError)
        }

        return isValid
    }

    private fun setFieldError(
        field: EditText,
        errorTextView: TextView,
        errorMessage: String
    ) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = errorMessage
        field.setBackgroundResource(R.drawable.et_border_error)
    }

    private fun setFieldNormal(field: EditText, errorTextView: TextView) {
        errorTextView.visibility = View.GONE
        field.setBackgroundResource(R.drawable.et_border_selector)
    }

    private fun setupPasswordVisibilityToggle(
        passwordField: EditText,
        visibleIcon: Int,
        hiddenIcon: Int
    ) {
        var isPasswordVisible = false

        passwordField.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // index for drawableRight
                val drawable = passwordField.compoundDrawables[drawableEnd]

                if (drawable != null && event.rawX >= (passwordField.right - drawable.bounds.width())) {
                    val selection = passwordField.selectionEnd
                    if (isPasswordVisible) {
                        passwordField.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
                        passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, hiddenIcon, 0)
                    } else {
                        passwordField.transformationMethod = null
                        passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, visibleIcon, 0)
                    }
                    isPasswordVisible = !isPasswordVisible
                    passwordField.setSelection(selection)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }


    private fun showError() {
        setFieldError(binding.etEmail, binding.tvEmailError, "Invalid email or password")
    }

    private fun showLoading() {
        binding.loginFragment.visibility = View.GONE
        binding.progressBarLogin.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loginFragment.visibility = View.VISIBLE
        binding.progressBarLogin.visibility = View.GONE
    }
}


