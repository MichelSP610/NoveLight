package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobePublicationStatus

@Entity(tableName = "Serie")
data class RoomSerie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("serie_id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo("favourite", defaultValue = "false")
    var favourite: Boolean,
    val publication_status: RanobePublicationStatus,
    val book_description: String,
    val imageFileName: String,
)
