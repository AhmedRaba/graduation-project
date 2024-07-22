package com.training.codespire.ui.frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.training.codespire.CategoryActivity
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        sharedPreferencesUtil=SharedPreferencesUtil(requireContext())


        navigateToCategory()

        return binding.root
    }


    private fun navigateToCategory() {


        binding.ivFrontEnd.setOnClickListener {
            openProductFragment(1)
        }
        binding.ivBackEnd.setOnClickListener {
            openProductFragment(2)
        }
        binding.ivAi.setOnClickListener {
            openProductFragment(3)
        }
        binding.ivMobile.setOnClickListener {
            openProductFragment(4)
        }
        binding.ivWebsite.setOnClickListener {
            openProductFragment(5)
        }
        binding.ivDesktop.setOnClickListener {
            openProductFragment(6)
        }
        binding.ivFree.setOnClickListener {
            openProductFragment(13)
        }

    }

    private fun openProductFragment(categoryId: Int) {
        val intent = Intent(requireContext(), CategoryActivity::class.java).apply {
            sharedPreferencesUtil.categoryId=categoryId
        }
        startActivity(intent)
    }


}