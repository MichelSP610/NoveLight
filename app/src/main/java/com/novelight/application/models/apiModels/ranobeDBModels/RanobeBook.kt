package com.novelight.application.models.apiModels.ranobeDBModels

import java.util.Date

data class RanobeBook(
    val id: Int,
    val title: String,
    val sort_order: Int,
    val c_release_date: Date,
    val image: RanobeImage,
    val releases: List<RanobeRelease>?
)
