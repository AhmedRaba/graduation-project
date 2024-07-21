package com.training.codespire.ui.frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.training.codespire.CategoryActivity
import com.training.codespire.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)



        navigateToCategory()

        return binding.root
    }


    private fun navigateToCategory() {


        binding.ivFrontEnd.setOnClickListener {
            openCategoryActivity(1)
        }
        binding.ivBackEnd.setOnClickListener {
            openCategoryActivity(2)
        }
        binding.ivAi.setOnClickListener {
            openCategoryActivity(3)
        }
        binding.ivMobile.setOnClickListener {
            openCategoryActivity(4)
        }
        binding.ivWebsite.setOnClickListener {
            openCategoryActivity(5)
        }
        binding.ivDesktop.setOnClickListener {
            openCategoryActivity(6)
        }
        binding.ivFree.setOnClickListener {
            openCategoryActivity(13)
        }

    }

    private fun openCategoryActivity(categoryId: Int) {
        val intent = Intent(requireContext(), CategoryActivity::class.java).apply {
            putExtra("CATEGORY_ID", categoryId)
        }
        startActivity(intent)
    }


}