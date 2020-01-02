package com.example.placemark.main

import android.app.Application
import com.example.placemark.models.*
import com.example.placemark.models.firebase.PlacemarkFireStore
import com.example.placemark.models.json.PlacemarkJSONStore
import com.example.placemark.room.PlacemarkStoreRoom
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp : Application(), AnkoLogger {

    lateinit var placemarks: PlacemarkStore

    lateinit var user: UserModel

    override fun onCreate() {

        super.onCreate()
      //  placemarks = PlacemarkJSONStore(applicationContext)
        //  placemarks = PlacemarkStoreRoom(applicationContext)

        placemarks = PlacemarkFireStore(applicationContext)

        info("Placemark started")
       // placemarks.add(PlacemarkModel("One", "About one..."))
       // placemarks.add(PlacemarkModel("Two", "About two..."))
        //placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}