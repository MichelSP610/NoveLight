package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeReleaseFormat
import java.util.Date

@Entity(tableName = "Release")
data class RoomRelease(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo("book_id")
    var bookId: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("romaji")
    val romaji: String?,
    @ColumnInfo("release_date")
    val release_date: Date,
    @ColumnInfo("pages")
    val pages: Int?,
    @ColumnInfo("format")
    val format: RanobeReleaseFormat,
    @ColumnInfo("last_page_read")
    val lastPageRead: Int
)
