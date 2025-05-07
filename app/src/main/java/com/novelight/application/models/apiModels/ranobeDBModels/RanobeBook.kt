package com.novelight.application.models.apiModels.ranobeDBModels

import java.util.Date

//TODO: language can be made an enum
data class RanobeBook(
    val id: Int,
    val title: String,
    val sort_order: Int,
    val c_release_date: Date,
    val image: RanobeImage,
    val lang: String,
    val releases: List<RanobeRelease>?
)
