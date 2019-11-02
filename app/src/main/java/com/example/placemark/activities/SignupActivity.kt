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
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // get reference to all views
        var et_user_name = findViewById(R.id.etname) as EditText
        var et_user_email = findViewById(R.id.etemail) as EditText
        var et_password = findViewById(R.id.etpassword) as EditText
        var et_studentid = findViewById(R.id.etstudentid) as EditText
        var btn_submit = findViewById(R.id.btnSignup) as Button


        // set on-click listener
        btnSignup.setOnClickListener {
            val user_name = et_user_name.text;
            val user_email = et_user_email.text;
            val studentid = et_studentid.text;
            val password = et_password.text;
            Toast.makeText(this@SignupActivity, user_name, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same

        }

        login.setOnClickListener{
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}