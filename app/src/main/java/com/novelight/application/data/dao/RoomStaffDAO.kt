package com.novelight.application.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novelight.application.data.entities.RoomStaff

@Dao
interface RoomStaffDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStaff(staff: RoomStaff)

    @Query("select * from Staff")
    fun getStaff(): LiveData<List<RoomStaff>>

}