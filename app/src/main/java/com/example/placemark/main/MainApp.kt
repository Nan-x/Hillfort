package com.example.placemark.main

import android.app.Application
import com.example.placemark.models.PlacemarkMemStore
import com.example.placemark.models.PlacemarkModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val placemarks = PlacemarkMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Placemark started")
       // placemarks.add(PlacemarkModel("One", "About one..."))
       // placemarks.add(PlacemarkModel("Two", "About two..."))
        //placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}