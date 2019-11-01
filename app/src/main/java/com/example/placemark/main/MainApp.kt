package com.example.placemark.main

import android.app.Application
import com.example.placemark.models.PlacemarkMemStore
import com.example.placemark.models.PlacemarkModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.example.placemark.models.PlacemarkJSONStore
import com.example.placemark.models.PlacemarkStore


class MainApp : Application(), AnkoLogger {

    lateinit var placemarks: PlacemarkStore

    override fun onCreate() {

        super.onCreate()
        placemarks = PlacemarkJSONStore(applicationContext)

        info("Placemark started")
       // placemarks.add(PlacemarkModel("One", "About one..."))
       // placemarks.add(PlacemarkModel("Two", "About two..."))
        //placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}