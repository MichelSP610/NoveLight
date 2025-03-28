package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Serie")
data class RoomSerie(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String
)
