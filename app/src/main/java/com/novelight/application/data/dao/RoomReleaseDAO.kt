package com.novelight.application.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.novelight.application.data.entities.RoomRelease

@Dao
interface RoomReleaseDAO {
    @Insert
    fun addRelease(release: RoomRelease)
}