package com.novelight.application.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novelight.application.data.entities.RoomRelease

@Dao
interface RoomReleaseDAO {
    @Query("select * from `Release` where id = :releaseId")
    fun getRelease(releaseId: Int): RoomRelease

    @Query("select count() from `Release` where id = :releaseId limit 1")
    fun countRelease(releaseId: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRelease(release: RoomRelease)

    @Query("update `Release` set title = :title, romaji = :romaji where id = :releaseId")
    fun updateRelease(releaseId: Int, title: String, romaji: String)
}