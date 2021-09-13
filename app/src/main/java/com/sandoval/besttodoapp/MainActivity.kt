package com.sandoval.besttodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()

    }

    override fun onSupportNavigateUp(): Boolean {
        return returnNavigateUp()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController)
    }

    private fun returnNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}