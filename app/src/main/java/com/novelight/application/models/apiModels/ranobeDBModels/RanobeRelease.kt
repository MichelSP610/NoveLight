package com.novelight.application.models.apiModels.ranobeDBModels

import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeReleaseFormat
import java.util.Date

data class RanobeRelease(
    val id: Int,
    val title: String,
    val romaji: String?,
    val release_date: Date,
    val pages: Int?,
    val format: RanobeReleaseFormat
)
