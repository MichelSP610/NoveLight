package com.novelight.application.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RoomBookWithRelease(
    @Embedded val book: RoomBook,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val releases: List<RoomRelease>
)
