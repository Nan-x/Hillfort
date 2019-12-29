package com.example.placemark.views.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import com.example.placemark.views.placemarklist.PlacemarkListView
import com.example.placemark.main.MainApp
import com.example.placemark.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var app: MainApp
    lateinit var user: UserModel

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var mProgressBar: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()



        // get ui  reference
        var et_email = findViewById(R.id.etemail) as EditText
        var et_password = findViewById(R.id.etpassword) as EditText
        var btnLogin = findViewById(R.id.btnLogin) as Button


        // set on-click listener
        btnLogin.setOnClickListener {
            val user_email = et_email.text.toString()
            val password = et_password.text.toString()
            Toast.makeText(this@LoginActivity, user_email, Toast.LENGTH_LONG).show()
            login(user_email, password)


        }

        signup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupView::class.java)
            startActivity(intent)
        }


    }
       private fun login(email: String, password: String){

           auth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(this) { task ->
                   if (task.isSuccessful) {

                       val email = auth.currentUser!!.email

                       app = application as MainApp
                       user = UserModel()

                       toast("success $email")
                       if (email != null) {
                           user.email = email
                           val intent = Intent(this@LoginActivity, PlacemarkListView::class.java)
                           startActivity(intent)

                       }

                   } else {
                       Toast.makeText(
                           baseContext, "Authentication failed.",
                           Toast.LENGTH_SHORT
                       ).show()
                   }
                   if (!task.isSuccessful) {
                       toast("unsuccessful")
                   }

               }


       }






}

