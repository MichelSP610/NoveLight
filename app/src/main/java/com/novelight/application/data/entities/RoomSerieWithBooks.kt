package com.novelight.application.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RoomSerieWithBooks(
    @Embedded val serie: RoomSerie,
    @Relation(
        parentColumn = "serie_id",
        entityColumn = "serie_id"
    )
    val books: List<RoomBook>,
    @Relation(
        parentColumn = "serie_id",
        entityColumn = "staff_name",
        associateBy = Junction(RoomSerieStaffCrossRef::class)
    )
    val staff: List<RoomStaff>
)
