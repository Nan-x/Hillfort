package com.example.placemark.views.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import com.example.placemark.views.placemarklist.PlacemarkListView
import com.example.placemark.main.MainApp
import com.example.placemark.models.UserModel
import com.example.placemark.views.BaseView
import com.example.placemark.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_location.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginView : BaseView() {


    lateinit var presenter: LoginPresenter


    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
       // init(toolbar, false)
        progressBar.visibility = View.GONE



        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter



        // get ui  reference
        var et_email = findViewById(R.id.etemail) as EditText
        var et_password = findViewById(R.id.etpassword) as EditText
        var btnLogin = findViewById(R.id.btnLogin) as Button


        // set on-click listener
        btnLogin.setOnClickListener {
            val user_email = et_email.text.toString()
            val password = et_password.text.toString()
            if (user_email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                presenter.login(user_email,password)
            }
        }

        signup.setOnClickListener {

            presenter.gotosignup()

        }


    }



}

