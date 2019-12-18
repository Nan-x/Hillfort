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
import com.example.placemark.helpers.readImageFromPath
import kotlinx.android.synthetic.main.activityplacemark.view.*

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: PlacemarkPresenter
    var placemark = PlacemarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityplacemark)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        presenter = PlacemarkPresenter(this)

        btnAdd.setOnClickListener {
            if (placemarkTitle.text.toString().isEmpty()) {
                toast(R.string.enter_placemark_title)
            } else {
                presenter.doAddOrSave(placemarkTitle.text.toString(), placemarkDescription.text.toString())
            }
        }

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        placemarkLocation.setOnClickListener { presenter.doSetLocation() }
    }

    fun showPlacemark(placemark: PlacemarkModel) {
        placemarkTitle.setText(placemark.title)
       placemarkDescription.setText(placemark.description)
        placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
        if (placemark.image != null) {
            chooseImage.setText(R.string.change_placemark_image)
        }
        btnAdd.setText(R.string.button_savePlacemark)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }
}