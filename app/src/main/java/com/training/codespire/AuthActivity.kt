package com.training.codespire

import com.training.codespire.data.datastore.SharedPreferencesUtil
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

private const val TAG = "AuthActivity"
class AuthActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen()
        setContentView(R.layout.activity_auth)

        sharedPreferencesUtil = SharedPreferencesUtil(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        if (sharedPreferencesUtil.isLoggedIn) {
            navigateToMainActivity()
        }
        

    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    
}