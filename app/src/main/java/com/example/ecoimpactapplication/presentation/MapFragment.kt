package com.example.ecoimpactapplication.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ecoimpactapplication.R
import com.example.ecoimpactapplication.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import permissions.dispatcher.ktx.LocationPermission
import permissions.dispatcher.ktx.PermissionsRequester
import permissions.dispatcher.ktx.constructLocationPermissionRequest


class MapFragment : Fragment(), OnMapReadyCallback {

    val mineralData = """
| Тип минерала | Географические координаты | Содержание Fe (%) | Содержание Cu (ppm) |
|--------------|---------------------------|-------------------|--------------------|
| Кварц        | 45.123, -75.456           | 20.5              | 5.2                |
| Гипс         | 36.789, -98.654           | 10.2              | 1.8                |
| Фельдспат    | 51.234, -112.345          | 15.7              | 3.0                |
"""

    val airQualityData = """
| Загрязнитель | Район      | Содержание (ppm) |
|--------------|------------|-------------------|
| SO2          | Город A    | 30                |
| NO2          | Город B    | 25                |
| CO2          | Сельская местность | 400           |
"""

    val soilData = """
| Тип почвы    | Географическая зона | Плодородие (%) | pH   |
|--------------|---------------------|-----------------|------|
| Чернозем     | Зона 1              | 5.8             | 6.5  |
| Песчаник     | Зона 2              | 3.2             | 7.2  |
"""

    val toxinData = """
| Токсин       | Географические координаты | Содержание (ppb) |
|--------------|---------------------------|-------------------|
| Свинец       | 40.567, -85.678           | 250               |
| Пестицид X   | 35.678, -120.567          | 50                |
"""

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
            val alertDialog = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Результат анализа")
                    .setView(R.layout.dialog_layout)
                    .setPositiveButton("OK") { dialog, which ->

                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                    .setMessage("Название базы данных: Экологическая база данных \"ЭкоМонитор\"\n" +
                            "\n" +
                            "Цель базы данных: Сбор, хранение и анализ данных о минералах, качестве воздуха, почве и токсинах в земле для поддержки экологических исследований и принятия экологических решений.\n" +
                            "\n" +
                            "Структура базы данных:\n" +
                            "\n" +
                            "    Минералы:\n" +
                            "        Типы минералов: кварц, гипс, фельдспат и др.\n" +
                            "        Распределение: Географическое распределение минералов с указанием координат и объемов находок.\n" +
                            "        Качество: Химический состав минералов, содержание полезных элементов (например, железо, медь, алюминий).\n" +
                            "\n" +
                            "    Качество воздуха:\n" +
                            "        Загрязнители: Содержание загрязнителей в воздухе, включая SO2, NO2, CO2.\n" +
                            "        Мониторинг: Данные о временных изменениях уровня загрязнителей в разные сезоны и в разных районах.\n" +
                            "\n" +
                            "    Почва:\n" +
                            "        Состав почвы: Плодородие, pH, содержание азота, фосфора и калия.\n" +
                            "        География почв: Распределение типов почв в различных климатических зонах.\n" +
                            "\n" +
                            "    Токсины в земле:\n" +
                            "        Токсичные вещества: Содержание тяжелых металлов (например, свинец, кадмий) и химических загрязнителей (например, пестициды).\n" +
                            "        Мониторинг: Данные о распределении токсинов в земле и их потенциальных воздействиях на окружающую среду.\n" +
                            "\n" +
                            "Примерные данные (в формате таблиц):\n$mineralData\n$airQualityData\n$soilData\n$toxinData")
                    .create()
            }

            alertDialog?.show()
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

        val loc1 = LatLng(41.369932, 69.451028)
        val loc2 = LatLng(41.366576, 69.454939)
        val loc3 = LatLng(41.367546, 69.458467)
        val loc4 = LatLng(41.370251, 69.457082)

        val loc5 = LatLng(41.350478152721664, 69.51716956495098)
        val loc6 = LatLng(41.34921891081876, 69.52558221443665)
        val loc8 = LatLng(41.34488040823433, 69.51642579851439)
        val loc7 = LatLng(41.34485283290832, 69.52553496575386)

        val loc9 = LatLng(41.39068908737996, 69.57006939892109)
        val loc10 = LatLng(41.38814011553606, 69.57317312727486)
        val loc12 = LatLng(41.385522148536786, 69.56430270837028)
        val loc11 = LatLng(41.383524155434756, 69.567369706211)

        val loc13 = LatLng(41.176068, 69.277425)
        val loc14 = LatLng(41.170574, 69.270325)
        val loc15 = LatLng(41.170927, 69.266534)
        val loc16 = LatLng(41.177716, 69.275311)

        val loc17 = LatLng(41.131700, 69.473280)
        val loc18 = LatLng(41.122155, 69.503000)
        val loc19 = LatLng(41.130061, 69.504392)
        val loc20 = LatLng(41.136213, 69.476620)

//        val polygon = map.addPolygon(
//            PolygonOptions()
//                .add(loc1, loc2, loc3, loc4)
//                .fillColor(Color.GREEN)
//        )
//
//        val polygon2 = map.addPolygon(
//            PolygonOptions()
//                .add(loc5, loc6, loc7, loc8)
//                .fillColor(Color.GREEN)
//        )
//
//        val polygon3 = map.addPolygon(
//            PolygonOptions()
//                .add(loc9, loc10, loc11, loc12)
//                .fillColor(Color.GREEN)
//        )
//
//        val polygon4 = map.addPolygon(
//            PolygonOptions()
//                .add(loc13, loc14, loc15, loc16)
//                .fillColor(Color.RED)
//        )

        val polygon5 = map.addPolygon(
            PolygonOptions()
                .add(loc17, loc18, loc19, loc20)
                .fillColor(Color.YELLOW)
        )

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