package com.chen.app.navigationtest.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.chen.app.R
import com.chen.basemodule.basem.BaseSimActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author CE Chen
 */
class BottomNavigationActivity : BaseSimActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val navigation: BottomNavigationView = findViewById(R.id.navigation) as BottomNavigationView
        val fragmentManager: FragmentManager = getSupportFragmentManager()
        val navHostFragment: NavHostFragment =
            fragmentManager.findFragmentById(R.id.frag_nav_bottom_navigation) as NavHostFragment
        val navController: NavController = navHostFragment.getNavController()
        NavigationUI.setupWithNavController(navigation, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.frag_nav_bottom_navigation).navigateUp()
    }
}