package com.novelight.application.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RoomSerieWithBooks(
    @Embedded val serie: RoomSerie,
    @Relation(
        parentColumn = "id",
        entityColumn = "serie_id"
    )
    val books: List<RoomBook>
)
