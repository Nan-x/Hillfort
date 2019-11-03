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
import com.example.placemark.helpers.showImagePicker
import com.example.placemark.main.MainApp
import com.example.placemark.models.Location
import com.example.placemark.models.PlacemarkModel
import org.jetbrains.anko.intentFor
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activityplacemark.view.*

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    var placemark = PlacemarkModel()
    var edit = false
    var delete = false

    val IMAGE_REQUEST = 1

    val LOCATION_REQUEST = 2


    internal var RegisterURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/simpleregister.php"
    private var etname: EditText? = null

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityplacemark)

        setSupportActionBar(findViewById(R.id.toolbarUp))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp


        if (intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            placemarkTitle.setText(placemark.title)
            placemarkDescription.setText(placemark.description)
            cb_single.isChecked = placemark.visited
            btnAdd.setText(R.string.button_savePlacemark)
        }

        if (intent.hasExtra("placemark delete")){
            delete = true
            placemark = intent.extras?.getParcelable<PlacemarkModel>("")!!
            app.placemarks.delete(placemark.copy())
        }



        btnDelete.setOnClickListener(){
            app.placemarks.delete(placemark.copy())
            toast("Placemark Deleted")
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

        addNotes.setOnClickListener {
           placemark.notes = placemarkDescription.text.toString()
        }



        cb_single.setOnClickListener(View.OnClickListener {
            if (cb_single.isChecked) {
                placemark.visited = true
                toast(cb_single.text.toString() + " Checked")
            } else {
                toast(cb_single.text.toString() + " UnChecked")
                placemark.visited = false
            }
        })



        chooseImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST)
        }



        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        toolbarUp.title = "Create New Hillfort"
        setSupportActionBar(toolbarUp)
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