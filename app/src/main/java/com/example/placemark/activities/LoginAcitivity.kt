package com.example.placemark.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import com.example.placemark.main.MainApp
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var app: MainApp

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var mProgressBar: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        // get reference to all views
        var et_email = findViewById(R.id.etemail) as EditText
        var et_password = findViewById(R.id.etpassword) as EditText
        var btnLogin = findViewById(R.id.btnLogin) as Button


        // set on-click listener
        btnLogin.setOnClickListener {
            val user_email = et_email.text;
            val password = et_password.text;
            Toast.makeText(this@LoginActivity, user_email, Toast.LENGTH_LONG).show()



        }

        signup.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

    }




}

