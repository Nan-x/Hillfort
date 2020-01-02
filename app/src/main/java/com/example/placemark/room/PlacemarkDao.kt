package com.example.placemark.room


import androidx.room.*
import com.example.placemark.models.PlacemarkModel

@Dao
interface PlacemarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(placemark: PlacemarkModel)

    @Query("SELECT * FROM PlacemarkModel")
    fun findAll(): List<PlacemarkModel>

    @Query("select * from PlacemarkModel where id = :id")
    fun findById(id: Long): PlacemarkModel

    @Update
    fun update(placemark: PlacemarkModel)

    @Delete
    fun deletePlacemark(placemark: PlacemarkModel)
}