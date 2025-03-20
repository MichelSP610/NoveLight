package com.novelight.application.models.apiModels.ranobeDBModels

import java.util.Date

data class RanobeBook(
    val id: Int,
    val title: String,
    val c_release_date: Date,
    val image: RanobeImage
)
