package com.salomao.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.salomao.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setNavigation()
    }

    private fun setNavigation() {
        navController = findNavController(R.id.retailNavHostFragment)
        navController.setGraph(R.navigation.main_nav_graph)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
