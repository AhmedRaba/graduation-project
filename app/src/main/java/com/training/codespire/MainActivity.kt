package com.training.codespire

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.training.codespire.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen()
        setContentView(binding.root)
        Log.e("onCreatehello", "onCreateView: ")
        dataStoreManager = DataStoreManager(this)

        setupNavigation()
    }

    private fun setupNavigation() {
        GlobalScope.launch {
            dataStoreManager.isLoggedIn.collect { isLoggedIn ->
                // Determine the start fragment based on login state
                val navController = findNavController(R.id.fragmentContainerView)

                val startDestination = if (isLoggedIn) R.id.homeFragment else R.id.loginFragment

                // Set the start destination in the navigation graph
                val graph = navController.navInflater.inflate(R.navigation.nav_graph)
                graph.setStartDestination(startDestination)
                navController.graph = graph
            }

        }
    }

}
