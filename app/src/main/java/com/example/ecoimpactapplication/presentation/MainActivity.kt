package com.example.ecoimpactapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoimpactapplication.R
import permissions.dispatcher.ktx.LocationPermission
import permissions.dispatcher.ktx.PermissionsRequester
import permissions.dispatcher.ktx.constructLocationPermissionRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment()
    }

    private fun changeFragment() {
        val mapFragment = MapFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, mapFragment)
        fragmentTransaction.commitAllowingStateLoss()
    }
}