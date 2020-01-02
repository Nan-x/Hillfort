package com.example.placemark.views.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import com.example.placemark.main.MainApp
import com.example.placemark.views.BaseView
import com.example.placemark.views.placemarklist.PlacemarkListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

class SignupView : BaseView() {

    lateinit var auth: FirebaseAuth
    lateinit var app: MainApp

    lateinit var presenter: SignupPresenter

    //val user = UserModel()

    //Firebase references
    var mDatabaseReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)



        app = application as MainApp

        auth = FirebaseAuth.getInstance()

        presenter = initPresenter(SignupPresenter(this)) as SignupPresenter

        //UI references
        //  var et_new_name = findViewById(R.id.et_new_name) as EditText
        var et_new_email = findViewById(R.id.et_new_email) as EditText
        var et_new_password = findViewById(R.id.et_new_password) as EditText
        //   var et_new_studentid = findViewById(R.id.et_new_studentid) as EditText
        var btn_Signup = findViewById(R.id.btnSignup) as Button



        btn_Signup.setOnClickListener() {
            //  val name = et_new_name.text.toString()
            val signup_email = et_new_email.text.toString()
            val password = et_new_password.text.toString()
            //   val studentid = et_new_studentid.text.toString()
            if (signup_email == "" || password == "") {
                toast("Please provide email + password")
            } else {
                presenter.signup(signup_email, password)
            }
        }



        login.setOnClickListener {

            presenter.gotologin()

        }
    }


}




 /*   private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@SignupView, PlacemarkListView::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignupView,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@SignupView,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    } */
