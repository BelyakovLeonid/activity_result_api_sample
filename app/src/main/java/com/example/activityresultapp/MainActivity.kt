package com.example.activityresultapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentContainer = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = (fragmentContainer as NavHostFragment).navController
        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.setOnNavigationItemReselectedListener {
            // Do nothing to ignore the reselection
        }
    }
}
