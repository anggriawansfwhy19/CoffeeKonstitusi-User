package com.anggriawans.coffeekonstitusi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var btnFinish: Button
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mapView = findViewById(R.id.map)
        btnFinish = findViewById(R.id.btnFinish)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        btnFinish.setOnClickListener {
            val selectedLocation = getSelectedLocation()

            saveLocationToPreferences(selectedLocation)

            val intent = Intent(this, ChooseLocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getSelectedLocation(): LatLng {
        // Logika untuk mendapatkan lokasi dari Google Maps, misalnya menggunakan LatLng
        return LatLng(-6.2088, 106.8456) // Ganti dengan logika sesuai kebutuhan Anda
    }

    private fun saveLocationToPreferences(location: LatLng) {
        // Simpan lokasi ke SharedPreferences
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("latitude", location.latitude.toFloat())
        editor.putFloat("longitude", location.longitude.toFloat())
        editor.apply()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set initial zoom level or other map configurations if needed
        val initialLatLng = LatLng(-6.2088, 106.8456) // Example location (Jakarta)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLatLng, 12f))

        // Set zoom controls
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Optionally, you can set custom zoom buttons
        // Set Zoom In button click listener
        findViewById<Button>(R.id.btnZoomIn).setOnClickListener {
            googleMap.animateCamera(CameraUpdateFactory.zoomIn())
        }

        // Set Zoom Out button click listener
        findViewById<Button>(R.id.btnZoomOut).setOnClickListener {
            googleMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
