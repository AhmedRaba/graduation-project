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
import com.training.codespire.data.MailSender
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentEnterEmailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnterEmailFragment : Fragment() {

    private lateinit var binding: FragmentEnterEmailBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private val authViewmodel: AuthViewmodel by viewModels()
    private val mailSender = MailSender()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterEmailBinding.inflate(inflater, container, false)
        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())

        binding.btnSendCode.setOnClickListener {
            val email = binding.etValidateEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                authViewmodel.checkEmail(email)
            } else {
                showToast("Please enter your email")
            }
        }

        authViewmodel.checkEmailResponseLiveData.observe(viewLifecycleOwner) { exists ->
            if (exists) {
                val code = generateRandomCode()
                sharedPreferencesUtil.emailCode = code
                val email = binding.etValidateEmail.text.toString().trim()
                sharedPreferencesUtil.email = email

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        mailSender.sendEmail(
                            from = "MS_LQxUVr@trial-3zxk54v05xxgjy6v.mlsender.net",
                            to = email,
                            subject = "Your Verification Code",
                            message = "Your verification code is $code"
                        )
                        showToast("Verification code sent")
                    } catch (e: Exception) {
                        showToast("Failed to send email: ${e.message}")
                    }

                    findNavController().navigate(R.id.action_enterEmailFragment_to_enterCodeFragment)
                }
            } else {
                showToast("Email does not exist")
            }
        }

        authViewmodel.errorLiveData.observe(viewLifecycleOwner) { error ->
            showToast(error)
        }

        return binding.root
    }

    private fun generateRandomCode(): String {
        return (1000..9999).random().toString()
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
