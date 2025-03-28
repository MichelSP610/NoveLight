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
    fun getSeries(): LiveData<List<RoomSerie>>

    @Query("select * from serie where id = :serieId")
    fun getSerie(serieId: Int): LiveData<RoomSerie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSerie(serie: RoomSerie)

    @Query("delete from Serie where id = :serieId")
    fun deleteSerie(serieId: Int)

    @Transaction
    @Query("select * from serie")
    fun getAllSeriesWithBooks(): LiveData<List<RoomSerieWithBooks>>

    @Transaction
    @Query("select * from Serie where id = :serieId")
    fun getSerieWithBooks(serieId: Int) : LiveData<RoomSerieWithBooks>
}