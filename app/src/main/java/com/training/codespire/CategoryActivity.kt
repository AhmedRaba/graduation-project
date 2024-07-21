package com.training.codespire

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.training.codespire.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment=supportFragmentManager
            .findFragmentById(R.id.category_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val categoryId = intent.getIntExtra("CATEGORY_ID", -1)

        val startDestination = getStartDestination(categoryId)

        navController.setGraph(R.navigation.nav_category, startDestination)

    }

    private fun getStartDestination(categoryId: Int): Bundle {
        return when (categoryId) {
            1 -> Bundle().apply { putInt("startDestination", R.id.frontEndFragment) }
            2 -> Bundle().apply { putInt("startDestination", R.id.backEndFragment) }
            3 -> Bundle().apply { putInt("startDestination", R.id.aiFragment) }
            4 -> Bundle().apply { putInt("startDestination", R.id.mobileFragment) }
            5 -> Bundle().apply { putInt("startDestination", R.id.webSiteFragment) }
            6 -> Bundle().apply { putInt("startDestination", R.id.desktopAppFragment) }
            13 -> Bundle().apply { putInt("startDestination", R.id.webSiteFragment) }
            else -> Bundle().apply { putInt("startDestination", R.id.freeFragment) }
        }
    }

}