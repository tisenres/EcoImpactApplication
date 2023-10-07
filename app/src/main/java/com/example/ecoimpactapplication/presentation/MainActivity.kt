package com.example.ecoimpactapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoimpactapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        changeFragment()

        setOnClickListeners()
    }

    private fun changeFragment() {
        val mapFragment = MapFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, mapFragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun setOnClickListeners() {
        binding.analyzeArea.setOnClickListener {

        }
    }
}