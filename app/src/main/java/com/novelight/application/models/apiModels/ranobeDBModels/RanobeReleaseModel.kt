package com.novelight.application.models.apiModels.ranobeDBModels

data class RanobeReleaseModel(
    val id: Int,
    val release_date: String,
    val title: String,
    val image: RanobeImage
)
