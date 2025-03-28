package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Book")
data class RoomBook(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "serie_id")
    var serieId: Int,
    var title: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: Date,
)
