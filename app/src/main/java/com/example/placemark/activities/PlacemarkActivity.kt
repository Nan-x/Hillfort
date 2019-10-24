package com.example.placemark.activities



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activityplacemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import com.example.placemark.R
import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    var placemark = PlacemarkModel()
    var edit = false

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityplacemark)
        app = application as MainApp

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            placemarkTitle.setText(placemark.title)
            placemarkDescription.setText(placemark.description)
            btnAdd.setText(R.string.button_savePlacemark)
        }

        btnAdd.setOnClickListener() {
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

            toolbarAdd.title = title
            setSupportActionBar(toolbarAdd)
            info("Placemark Activity started..")

            /*  btnDelete.setOnClickListener() {
      placemark.title = placemarkTitle.text.toString()
      if (placemark.title.isNotEmpty()) {
        info("add Button Pressed: $placemark")
      }
      else {
        toast ("Please Enter a HillFort Name")
      }
    }*/

        }

        }
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
}