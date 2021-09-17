package com.sandoval.besttodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()

    }

    override fun onSupportNavigateUp(): Boolean {
        return returnNavigateUp()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    private fun returnNavigateUp(): Boolean {
        mSharedViewModel.hideSoftKeyboard(this)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}