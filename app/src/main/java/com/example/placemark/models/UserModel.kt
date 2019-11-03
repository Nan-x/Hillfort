package com.example.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id: Long = 0, var name: String = "", var username: String = "",     var email: String = "",   var password: String = "",   var studentid: String = "") : Parcelable

