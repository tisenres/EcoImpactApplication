package com.example.ecoimpactapplication.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecoimpactapplication.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import permissions.dispatcher.ktx.LocationPermission
import permissions.dispatcher.ktx.PermissionsRequester
import permissions.dispatcher.ktx.constructLocationPermissionRequest


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var permissionRequester: PermissionsRequester
    private lateinit var locationManager: LocationManager
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(layoutInflater)
        val view = binding.root

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        permissionRequester = constructLocationPermissionRequest(
            permissions = arrayOf(LocationPermission.FINE),
            onShowRationale = null,
            onPermissionDenied = null,
            onNeverAskAgain = null,
            requiresPermission = ::receiveLocation
        )

        return view
    }

    @SuppressLint("MissingPermission")
    private fun receiveLocation() {
        map.isMyLocationEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.analyzeArea.setOnClickListener {
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        map = gMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true
        map.uiSettings.isScrollGesturesEnabled = true

        permissionRequester.launch()
        pushMarkers()

    }

    private fun pushMarkers() {

        val defaultLocation = LatLng(0.0, 0.0)
        val defaultLocation2 = LatLng(0.0, 40.0)
        val defaultLocation3 = LatLng(40.0, 40.0)
        val defaultLocation4 = LatLng(40.0, 0.0)
        val defaultLocation5 = LatLng(-60.0, -40.0)

        val polygon = map.addPolygon(
            PolygonOptions()
                .add(defaultLocation, defaultLocation2, defaultLocation3, defaultLocation4)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE)
        )

        val circle = map.addCircle(
            CircleOptions().center(defaultLocation5)
                .radius(10000.0)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE)
        )

        polygon.isClickable = true
        circle.isClickable = true

        map.setOnCircleClickListener { circle ->
            circle.fillColor = Color.YELLOW
//            circle.fillColor = Color.alpha(Color.RED, 80)
            circle.radius = 1000000.0
        }

        map.setOnPolygonClickListener { polygon ->
            polygon.fillColor = Color.WHITE
            polygon.strokeColor = Color.BLACK
        }

    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}