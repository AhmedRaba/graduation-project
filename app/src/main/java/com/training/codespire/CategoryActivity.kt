package com.training.codespire

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.training.codespire.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.category_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        val categoryId = intent.getIntExtra("CATEGORY_ID", -1)
        Log.e("CategoryActivity", categoryId.toString())


        if (categoryId != -1) {
            val startDestination = when (categoryId) {
                1 -> R.id.frontEndFragment
                2 -> R.id.backEndFragment
                3 -> R.id.aiFragment
                4 -> R.id.mobileFragment
                5 -> R.id.webSiteFragment
                6 -> R.id.desktopAppFragment
                13 -> R.id.freeFragment
                else -> R.id.aiFragment // Default start destination
            }
            val navGraph = navController.navInflater.inflate(R.navigation.nav_category)
            navGraph.setStartDestination(startDestination)
            navController.graph = navGraph
        }else{
            Log.e("CategoryActivity", "Invalid category ID")
        }

    }

}