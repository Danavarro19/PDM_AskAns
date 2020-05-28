package com.dnavarro.askanswerviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dnavarro.askanswerviews.databinding.ActivityMainBinding
import com.dnavarro.askanswerviews.retrofit.serviceLoginResponse
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceLoginResponse.init(this.application)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout



        val navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
//        navController.addOnDestinationChangedListener{ _,destination,_ ->
//                when (destination.id){
//                    R.id.action_register_to_fragment_home -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
//                    R.id.action_frontPage_to_fragment_home -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
//                    R.id.action_frontPage_to_register -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//                }
//        }
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}

