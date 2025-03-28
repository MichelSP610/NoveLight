package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date

@Entity(tableName = "Book", primaryKeys = ["id", "serie_id"])
data class RoomBook(
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "serie_id")
    var serieId: Int,
    var title: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: Date,
)
