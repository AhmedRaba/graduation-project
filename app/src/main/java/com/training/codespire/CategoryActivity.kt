package com.training.codespire

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesUtil=SharedPreferencesUtil(this)



        val categoryId = sharedPreferencesUtil.categoryId
        Log.e("CategoryActivity", categoryId.toString())




    }

}