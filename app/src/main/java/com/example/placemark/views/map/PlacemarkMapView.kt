package com.example.placemark.views.map

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.placemark.R
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.BaseView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

import kotlinx.android.synthetic.main.content_placemark_map.*
import kotlinx.android.synthetic.main.activity_placemark_map.*

class PlacemarkMapView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: PlacemarkMapPresenter
    lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark_map)
        super.init(toolbar, true)

        presenter = initPresenter (PlacemarkMapPresenter(this)) as PlacemarkMapPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadPlacemarks()
        }
    }

    override fun showPlacemark(placemark: PlacemarkModel) {
        currentTitle.text = placemark.title
        currentDescription.text = placemark.description
        Glide.with(this).load(placemark.image).into(currentImage);    }

    override fun showPlacemarks(placemarks: List<PlacemarkModel>) {
        presenter.doPopulateMap(map, placemarks)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
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
}