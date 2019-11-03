package com.example.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_placemark_list.*
import com.example.placemark.R
import com.example.placemark.activities.adapters.PlacemarkAdapter
import com.example.placemark.activities.adapters.PlacemarkListener
import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class PlacemarkListActivity : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark_list)
        app = application as MainApp
        auth = FirebaseAuth.getInstance()

        toolbar.title = title
        setSupportActionBar(toolbar)


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadPlacemarks()




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<PlacemarkActivity>(0)
            R.id.logOut ->  logout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel) {
        startActivityForResult(intentFor<PlacemarkActivity>().putExtra("placemark_edit", placemark), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPlacemarks()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadPlacemarks() {
        showPlacemarks(app.placemarks.findAll())
    }

    fun showPlacemarks (placemarks: List<PlacemarkModel>) {
        recyclerView.adapter = PlacemarkAdapter(placemarks, this)
        recyclerView.adapter?.notifyDataSetChanged()


    }
    fun logout() {
        auth.signOut()
        toast("You have been logged out")
        val intent = Intent(this@PlacemarkListActivity, LoginActivity::class.java)
        startActivity(intent)
        toast("Please log in to view Hillforts")
    }
}






