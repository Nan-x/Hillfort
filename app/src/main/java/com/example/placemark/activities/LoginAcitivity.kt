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
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // get reference to all views
        var et_email = findViewById(R.id.etemail) as EditText
        var et_password = findViewById(R.id.etpassword) as EditText
        var btnLogin = findViewById(R.id.btnLogin) as Button


        // set on-click listener
        btnLogin.setOnClickListener {
            val user_email = et_email.text;
            val password = et_password.text;
            Toast.makeText(this@LoginActivity, user_email, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same

        }

        signup.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

    }

}

