package com.example.placemark.views.register

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.placemark.main.MainApp
import com.example.placemark.models.UserModel
import com.example.placemark.models.firebase.PlacemarkFireStore
import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.VIEW
import com.example.placemark.views.placemarklist.PlacemarkListView
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast

class LoginPresenter(view: BaseView) : BasePresenter(view) {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    var fireStore: PlacemarkFireStore? = null

    init {
        if (app.placemarks is PlacemarkFireStore) {
            fireStore = app.placemarks as PlacemarkFireStore
        }
    }

    fun login(email: String, password: String){

        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchPlacemarks {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun gotosignup(){
        view?.navigateTo(VIEW.SIGNUP)
    }

    }






