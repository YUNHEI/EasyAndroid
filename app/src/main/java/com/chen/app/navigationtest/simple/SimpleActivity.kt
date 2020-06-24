package com.chen.app.navigationtest.simple

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.chen.app.R
import com.chen.basemodule.basem.BaseSimActivity

/**
 * @author CE Chen
 */
class SimpleActivity : BaseSimActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)fragmentManager.findFragmentById(R.id.frag_nav_simple);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(SimpleActivity.this, navController);*/
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frag_nav_simple) as NavHostFragment?
        val navSimple =
            navHostFragment!!.navController.navInflater.inflate(R.navigation.nav_simple)
        val firstFragDestination = navSimple.findNode(R.id.nav_simple_first_frag)
//        val fragmentArgs: FirstFragmentArgs = FirstFragmentArgs.Builder("给 First 的数据").build()
//        firstFragDestination.addInDefaultArgs( fragmentArgs.toBundle())
        navHostFragment.navController.graph = navSimple
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.frag_nav_simple).navigateUp()
    }
}