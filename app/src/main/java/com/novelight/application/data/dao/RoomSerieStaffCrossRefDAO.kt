package com.novelight.application.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novelight.application.data.entities.RoomSerieStaffCrossRef

@Dao
interface RoomSerieStaffCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSerieStaffCrossRef(serieStaffCrossRef: RoomSerieStaffCrossRef)

    @Query("delete from seriestaffcrossref")
    fun deleteAllSerieStaffCrossRef()
}