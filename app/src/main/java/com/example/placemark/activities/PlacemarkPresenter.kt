package com.example.placemark.activities

import android.content.Intent
import com.example.placemark.models.Location
import org.jetbrains.anko.intentFor
import com.example.placemark.helpers.showImagePicker
import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel

class PlacemarkPresenter(val view: PlacemarkActivity) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var placemark = PlacemarkModel()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = view.intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            view.showPlacemark(placemark)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        placemark.title = title
        placemark.description = description
        if (edit) {
            app.placemarks.update(placemark)
        } else {
            app.placemarks.create(placemark)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.placemarks.delete(placemark)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (placemark.zoom != 0f) {
            location.lat = placemark.lat
            location.lng = placemark.lng
            location.zoom = placemark.zoom
        }
        view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                placemark.image = data.data.toString()
                view.showPlacemark(placemark)
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                placemark.lat = location.lat
                placemark.lng = location.lng
                placemark.zoom = location.zoom
            }
        }
    }
}