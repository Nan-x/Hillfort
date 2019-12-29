package com.example.placemark.views.placemark


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activityplacemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import com.example.placemark.R
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.BaseView
import kotlinx.android.synthetic.main.activityplacemark.description
import kotlinx.android.synthetic.main.activityplacemark.placemarkTitle
import kotlinx.android.synthetic.main.card_placemark.*

class PlacemarkView : BaseView(), AnkoLogger {


    lateinit var presenter: PlacemarkPresenter
    var placemark = PlacemarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityplacemark)
        super.init(toolbarAdd)

        presenter = initPresenter (PlacemarkPresenter(this)) as PlacemarkPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }

        }

        chooseImage.setOnClickListener { presenter.doSelectImage() }

      //  placemarkLocation.setOnClickListener { presenter.doSetLocation() }
    }

    override fun showPlacemark(placemark: PlacemarkModel) {
        placemarkTitle.setText(placemark.title)
        description.setText(placemark.description)
        placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
        if (placemark.image != null) {
            chooseImage.setText(R.string.change_placemark_image)
        }
        lat.setText("%.6f".format(placemark.lat))
        lng.setText("%.6f".format(placemark.lng))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                if (placemarkTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_placemark_title)
                } else {
                    presenter.doAddOrSave(placemarkTitle.text.toString(), description.text.toString())
                }
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

    override fun onBackPressed() {
        presenter.doCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        presenter.doResartLocationUpdates()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}


