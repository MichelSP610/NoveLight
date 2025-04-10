package com.novelight.application.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.novelight.application.data.entities.RoomBook
import com.novelight.application.data.entities.RoomBookWithRelease

@Dao
interface RoomBookDAO {
    @Query("select * from Book")
    fun getBooks(): LiveData<List<RoomBook>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBook(roomBook: RoomBook)

    @Transaction
    @Query("select * from Book where id = :bookId")
    fun getBookWithReleases(bookId: Int): LiveData<RoomBookWithRelease>

}