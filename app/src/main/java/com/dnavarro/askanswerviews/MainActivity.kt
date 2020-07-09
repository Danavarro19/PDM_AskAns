package com.dnavarro.askanswerviews

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dnavarro.askanswerviews.databinding.ActivityMainBinding
import com.dnavarro.askanswerviews.retrofit.serviceLoginResponse
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_register.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val REQUEST_CODE_LOCATION = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            // REQUEST_CODE_LOCATION should be defined on your app level

            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_LOCATION)
        }


        serviceLoginResponse.init(this.application)
        val model: Userviewmodel by viewModels()
        model.getLanzamientos()


        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout



        val navController = this.findNavController(R.id.myNavHostFragment)

        navController.addOnDestinationChangedListener{ _,destination,_ ->
            println(destination.id)
            when (destination.id){

                R.id.frontPage -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                R.id.fragment_home -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                R.id.action_frontPage_to_register -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                R.id.action_register_to_frontPage -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                else -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
        model.resetLanzamientos()


        model.closeSesion.observe(this, Observer {
            if(it){
                println("cerrar sesion: Main")
                findNavController(R.id.myNavHostFragment).navigate(R.id.frontPage)
            }
        })
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

//        binding.navView.setOnDragListener { v, event ->
//            println(event)
//            model.resetLanzamientos()
//            true
//
//        }

        model.saldoFix.observe(this, Observer {
//            if(it != null){
//
//            }
            binding.navView.menu.findItem(R.id.saldo).title = "Saldo $it"
        })
        binding.navView.setNavigationItemSelectedListener {
            println("ItemMain Selected: "+it.itemId)
            when(it.itemId){
                R.id.cuenta ->{
                    findNavController(R.id.myNavHostFragment).navigate(R.id.fragmentPerfil)

                    true
                }
                R.id.cerrarSesion -> {
                    model.cerrarSesion()
                    true
                }
                R.id.pagos -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.fragment_lanzamientos)
                    true
                }
                else -> true
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION && grantResults.isNotEmpty()
            && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            throw RuntimeException("Location services are required in order to " + "connect to a reader.")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}

