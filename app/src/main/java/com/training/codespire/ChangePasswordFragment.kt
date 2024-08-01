package com.training.codespire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentChangePasswordBinding
import com.training.codespire.network.change_password.ChangePasswordRequest

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private val authViewmodel: AuthViewmodel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())

        binding.btnSaveNewPassword.setOnClickListener {
            val newPassword = binding.etNewPassword.text.toString().trim()
            val confirmNewPassword = binding.etConfirmNewPassword.text.toString().trim()

            if (newPassword.isNotEmpty() && newPassword == confirmNewPassword) {
                val email = sharedPreferencesUtil.email.toString()
                val changePasswordRequest = ChangePasswordRequest(newPassword, confirmNewPassword)
                authViewmodel.changePassword(email, changePasswordRequest)
            } else {
                showToast("Passwords do not match or are empty")
            }
        }

        authViewmodel.changePasswordLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                showToast("Password changed successfully")
                findNavController().navigate(R.id.action_changePasswordFragment_to_loginFragment)
            }
        }

        authViewmodel.errorLiveData.observe(viewLifecycleOwner) { error ->
            showToast("Error: $error")
        }

        return binding.root
    }

    private fun showToast(message: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast_layout, null)
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }


}