package com.example.placemark.views.register

import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast

class SignupPresenter(view: BaseView) : BasePresenter(view) {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun signup(email: String, password: String){

       // view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
               // view?.hideProgress()

                view?.navigateTo(VIEW.LIST)
            } else {
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
          //  view?.hideProgress()
        }
    }

    fun gotologin(){
        view?.navigateTo(VIEW.LOGIN)
    }

}