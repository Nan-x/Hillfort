package com.example.placemark.activities



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activityplacemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import com.example.placemark.R
import com.example.placemark.helpers.readImage
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.helpers.showImagePicker
import com.example.placemark.main.MainApp
import com.example.placemark.models.Location
import com.example.placemark.models.PlacemarkModel
import org.jetbrains.anko.intentFor
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.placemark.helpers.PreferenceHelper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.HashMap
import com.example.placemark.activities.LoginActivity

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    var placemark = PlacemarkModel()
    var edit = false
    var delete = false

    val IMAGE_REQUEST = 1

    val LOCATION_REQUEST = 2


    internal var RegisterURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/simpleregister.php"
    private var etname: EditText? = null

    private var etusername:EditText? = null
    private var etpassword:EditText? = null
    private var btnregister: Button? = null
    private var tvlogin: TextView? = null
    private var preferenceHelper: PreferenceHelper? = null
    private val RegTask = 1
    private var mProgressDialog: ProgressDialog? = null


    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityplacemark)
        app = application as MainApp

        preferenceHelper = PreferenceHelper(this)


    /*    if (preferenceHelper!!.getIsLogin()) {
            val intent = Intent(this@PlacemarkActivity, PlacemarkListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }


        etname = findViewById<View>(R.id.etname) as EditText
        etusername = findViewById<View>(R.id.etusername) as EditText
        etpassword = findViewById<View>(R.id.etpassword) as EditText

        btnregister = findViewById<View>(R.id.btn) as Button
        tvlogin = findViewById<View>(R.id.tvlogin) as TextView

        tvlogin!!.setOnClickListener {
            val intent = Intent(this@PlacemarkActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnregister!!.setOnClickListener {
            try {
                register()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

*/

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            placemarkTitle.setText(placemark.title)
            placemarkDescription.setText(placemark.description)
            btnAdd.setText(R.string.button_savePlacemark)
        }

        if (intent.hasExtra("placemark delete")){
            delete = true
            placemark = intent.extras?.getParcelable<PlacemarkModel>("")!!
            app.placemarks.
        }



            btnAdd.setOnClickListener() {
                placemark.title = placemarkTitle.text.toString()
                placemark.description = placemarkDescription.text.toString()
                if (placemark.title.isEmpty()) {
                    toast(R.string.enter_placemark_title)
                } else {
                    if (edit) {
                        app.placemarks.update(placemark.copy())
                    } else {
                        app.placemarks.create(placemark.copy())
                    }
                }
                info("add Button Pressed: $placemarkTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }



        placemarkLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (placemark.zoom != 0f) {
                location.lat =  placemark.lat
                location.lng = placemark.lng
                location.zoom = placemark.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }


        cb_single.setOnClickListener(View.OnClickListener {
            if (cb_single.isChecked) {
                toast(cb_single.text.toString() + " Checked")
            } else {
                toast(cb_single.text.toString() + " UnChecked")
            }
        })



        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }



        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Placemark Activity started..")


    }



 /*   @Throws(IOException::class, JSONException::class)
    private fun register() {

        showSimpleProgressDialog(this@PlacemarkActivity, null, "Loading...", false)

        try {

            Fuel.post(
                RegisterURL, listOf(
                    "name" to etname!!.text.toString()
                    , "username" to etusername!!.text.toString()
                    , "password" to etpassword!!.text.toString()
                )
            ).responseJson { request, response, result ->
                Log.d("plzzzzz", result.get().content)
                onTaskCompleted(result.get().content, RegTask)
            }
        } catch (e: Exception) {

        } finally {

        }
    }




    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("responsejson", response)
        removeSimpleProgressDialog()
        when (task) {
            RegTask -> if (isSuccess(response)) {
                saveInfo(response)
                Toast.makeText(this@PlacemarkActivity, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@PlacemarkActivity, PlacemarkListActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            } else {
                Toast.makeText(this@PlacemarkActivity, getErrorMessage(response), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveInfo(response: String) {
        preferenceHelper!!.putIsLogin(true)
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "true") {
                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {

                    val dataobj = dataArray.getJSONObject(i)
                    preferenceHelper!!.putName(dataobj.getString("name"))

                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun isSuccess(response: String): Boolean {
        try {
            val jsonObject = JSONObject(response)
            return if (jsonObject.optString("status") == "true") {
                true
            } else {

                false
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return false
    }

    fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "No data"
    }

    fun showSimpleProgressDialog(context: Context, title: String?, msg: String, isCancelable: Boolean) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg)
                mProgressDialog!!.setCancelable(isCancelable)
            }
            if (!mProgressDialog!!.isShowing) {
                mProgressDialog!!.show()
            }

        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                    mProgressDialog = null
                }
            }
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()

        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

*/











        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    placemark.image = data.getData().toString()
                    placemarkImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_placemark_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    placemark.lat = location.lat
                    placemark.lng = location.lng
                    placemark.zoom = location.zoom
                }
            }
        }
    }
}