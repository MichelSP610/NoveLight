package com.novelight.application.models.apiModels.ranobeDBModels

import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeStaffRoleType

data class RanobeStaff(
    val role_type: RanobeStaffRoleType,
    val name: String,
    val romaji: String?
)
