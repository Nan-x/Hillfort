package com.example.placemark.main

import android.app.Application
import com.example.placemark.models.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp : Application(), AnkoLogger {

    lateinit var placemarks: PlacemarkStore

    lateinit var user: UserModel

    override fun onCreate() {

        super.onCreate()
        placemarks = PlacemarkJSONStore(applicationContext)

        info("Placemark started")
       // placemarks.add(PlacemarkModel("One", "About one..."))
       // placemarks.add(PlacemarkModel("Two", "About two..."))
        //placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}