package com.example.placemark.activities

import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


class PlacemarkListPresenter(val view: PlacemarkListActivity) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getPlacemarks() = app.placemarks.findAll()

    fun doAddPlacemark() {
        view.startActivityForResult<PlacemarkActivity>(0)
    }

    fun doEditPlacemark(placemark: PlacemarkModel) {
        view.startActivityForResult(view.intentFor<PlacemarkActivity>().putExtra("placemark_edit", placemark), 0)
    }

    fun doShowPlacemarksMap() {
        view.startActivity<PlacemarkMapActivity>()
    }
}