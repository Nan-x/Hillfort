package com.example.placemark.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.android.synthetic.main.content_placemark_map.*
import kotlinx.android.synthetic.main.activity_placemark_map.*

class PlacemarkMapActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener  {

    lateinit var map: GoogleMap

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        app = application as MainApp

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark_map)
        toolbar.title = title
        setSupportActionBar(toolbar)
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        app.placemarks.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.setOnMarkerClickListener(this)

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))

        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val placemark = app.placemarks.findById(tag)
        currentTitle.text = placemark!!.title
        currentDescription.text = placemark!!.description
        currentImage.setImageBitmap(readImageFromPath(this, placemark.image))
        return true
    }
}
