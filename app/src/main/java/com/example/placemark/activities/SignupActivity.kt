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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class SignupActivity : AppCompatActivity() {






    lateinit var app: MainApp

    val user = UserModel()

    //Firebase references
    var mDatabaseReference: DatabaseReference? = null
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        app = application as MainApp



        btnSignup.setOnClickListener() {
           user.name = et_new_name.text.toString()
           user. email = et_new_email.text.toString()
            user.password = et_new_password.text.toString()
            user.studentid = et_new_studentid.text.toString()


            if (!TextUtils.isEmpty(user.name) && !TextUtils.isEmpty(user.studentid)
                && !TextUtils.isEmpty(user.email) && !TextUtils.isEmpty(user.password)
            ) {

            } else {
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
            }




                auth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid
                        //Verify Email
                        verifyEmail();
                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("Name").setValue(user.name)
                        currentUserDb.child("Username").setValue(user.studentid)
                        updateUserInfoAndUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@SignupActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }





            login.setOnClickListener {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }

        }


    }

    private fun updateUserInfoAndUI() {
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
    }
}