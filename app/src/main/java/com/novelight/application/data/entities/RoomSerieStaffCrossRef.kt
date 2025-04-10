package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity("SerieStaffCrossRef", primaryKeys = ["serie_id", "staff_name"])
data class RoomSerieStaffCrossRef(
    @ColumnInfo("serie_id")
    var serieId: Int,
    @ColumnInfo("staff_name")
    var staffName: String
)
