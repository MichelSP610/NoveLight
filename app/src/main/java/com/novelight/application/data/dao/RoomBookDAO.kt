package com.novelight.application.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.novelight.application.data.entities.RoomBook

@Dao
interface RoomBookDAO {
    @Query("select * from Book")
    fun getBooks(): LiveData<List<RoomBook>>

}