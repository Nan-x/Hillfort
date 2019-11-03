package com.example.placemark.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import com.example.placemark.main.MainApp
import com.example.placemark.models.UserModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class SignupActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var app: MainApp

    //val user = UserModel()

    //Firebase references
    var mDatabaseReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        app = application as MainApp

        auth = FirebaseAuth.getInstance()

        //UI references
      //  var et_new_name = findViewById(R.id.et_new_name) as EditText
        var et_new_email = findViewById(R.id.et_new_email) as EditText
        var et_new_password = findViewById(R.id.et_new_password) as EditText
     //   var et_new_studentid = findViewById(R.id.et_new_studentid) as EditText
        var btn_Signup = findViewById(R.id.btnSignup) as Button



        btn_Signup.setOnClickListener() {
            val name = et_new_name.text.toString()
            val signup_email = et_new_email.text.toString()
            val password = et_new_password.text.toString()
            val studentid = et_new_studentid.text.toString()



            createNewAccount(signup_email, password )
        }


        login.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }


    }


    private fun createNewAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    /*  if (!TextUtils.isEmpty(user.name) &&
                            !TextUtils.isEmpty(user.studentid) &&
                            !TextUtils.isEmpty(user.email) &&
                            !TextUtils.isEmpty(user.password)) {
                                 -CREATE ACCOUNT-
                       } else {
                       Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }*/
                    val user = auth.currentUser!!
                    auth.signOut()

                    toast("New User Success $user")

                    val intentRegister = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intentRegister)

                } else {
                    // If sign up fails, display a message to the user.
                    toast("Account Could Not Be Created")

                }

            }

        }


    }

 /*   private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@SignupActivity, PlacemarkListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignupActivity,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@SignupActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    } */
