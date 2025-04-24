package com.novelight.application.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeStaffRoleType

@Entity("Staff")
data class RoomStaff(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("staff_name")
    var name: String,
    var roleType: RanobeStaffRoleType,
    var romaji: String?
)
