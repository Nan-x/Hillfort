package com.example.placemark.models


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PlacemarkModel(@PrimaryKey(autoGenerate = true)
                          var id: Long = 0,
                          var fbId : String = "",
                          var title: String = "",
                          var description: String = "",
                          var image: String = "",
                          var visited: Boolean = false,
                          var notes: String = "",
                          @Embedded var location : Location = Location()): Parcelable

