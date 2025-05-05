package com.novelight.application.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieWithBooks

@Dao
interface RoomSerieDAO {
    @Query("select * from serie")
    fun getSeries(): List<RoomSerie>

    @Query("select * from serie where favourite = 1")
    fun getFavouriteSeries(): List<RoomSerie>

    @Query("select * from serie where serie_id = :serieId limit 1")
    fun getSerie(serieId: Int): RoomSerie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSerie(serie: RoomSerie)

    @Query("update serie set favourite = :toggle where serie_id = :serieId")
    fun updateSerieInLibrary(serieId: Int, toggle: Boolean)

    @Query("delete from Serie where serie_id = :serieId")
    fun deleteSerie(serieId: Int)

    @Transaction
    @Query("select * from serie")
    fun getAllSeriesWithBooks(): LiveData<List<RoomSerieWithBooks>>

    @Transaction
    @Query("select * from Serie where serie_id = :serieId")
    fun getSerieWithBooks(serieId: Int) : RoomSerieWithBooks

    @Query("SELECT title FROM serie WHERE favourite = 1")
    suspend fun getFavoriteSerieTitles(): List<String>

}