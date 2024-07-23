package com.training.codespire.ui.frags

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.training.codespire.AuthActivity
import com.training.codespire.R
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentProfileBinding
import kotlin.random.Random

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var authViewModel: AuthViewmodel
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]

        authViewModel.logoutResponseLiveData.observe(viewLifecycleOwner) { isLoggedOut ->
            hideLoading()
            if (isLoggedOut) {
                sharedPreferencesUtil.clear()
                navigateToLogin()
            } else {
                Log.e("AuthViewModel", "Logout failed")
            }
        }

        // Observe the error LiveData
        authViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            hideLoading()
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }


        logout()
        showAboutUs()
        setUsername()

        return binding.root
    }


    private fun logout() {
        binding.profileLogOut.setOnClickListener {
            Log.d("ProfileFragment", "Logging out...")
            showLoading()
            authViewModel.logoutUser()
        }
    }


    private fun navigateToLogin() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showLoading() {
        binding.profileFragment.visibility = View.GONE
        binding.progressBarProfile.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.profileFragment.visibility = View.VISIBLE
        binding.progressBarProfile.visibility = View.GONE
    }

    private fun setUsername() {
        binding.tvProfileUsername.text = sharedPreferencesUtil.username
        val firstLetter = sharedPreferencesUtil.username?.firstOrNull().toString()
        binding.profileAvatar.avatarInitials = firstLetter
        binding.profileAvatar.avatarInitialsBackgroundColor = getRandomColor()
    }

    private fun getRandomColor(): Int {
        val random = Random
        return Color.argb(
            255, // alpha
            random.nextInt(256), // red
            random.nextInt(256), // green
            random.nextInt(256) // blue
        )
    }


    private fun showAboutUs() {

        binding.proileAboutUs.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_about_us, null)
            val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView).create()
            dialogView.findViewById<Button>(R.id.btn_about_us_close).setOnClickListener {
                dialogBuilder.dismiss()
            }
            dialogBuilder.show()
        }

    }


}
