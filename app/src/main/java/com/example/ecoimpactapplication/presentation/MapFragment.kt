package com.example.ecoimpactapplication.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecoimpactapplication.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

        pushMarkers()
        permissionRequester.launch()

    }

    private fun pushMarkers() {

        val defaultLocation = LatLng(37.7749, -122.4194)
        map.addMarker(
            MarkerOptions()
                .position(defaultLocation)
                .title("Marker in Sydney")
        )

        val defaultLocation2 = LatLng(78.7749, -175.4194)
        map.addMarker(
            MarkerOptions()
                .position(defaultLocation2)
                .title("Marker in Sydney")
        )

        val defaultLocation3 = LatLng(39.7749, -127.4194)
        map.addMarker(
            MarkerOptions()
                .position(defaultLocation3)
                .title("Marker in Sydney")
        )

        val defaultLocation4 = LatLng(8.7749, 43.4194)
        map.addMarker(
            MarkerOptions()
                .position(defaultLocation4)
                .title("Marker in Sydney")
        )
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15F));

    }

    private val locationListener = LocationListener { location ->
        val latitude = location.latitude
        val longitude = location.longitude
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15.0f))
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
//        if (
//            context?.let {context ->
//                ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            } == true) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.toFloat(), locationListener)
//        }
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