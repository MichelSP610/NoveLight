package com.novelight.application.models.apiModels.ranobeDBModels

data class RanobeSerieModel(
    val id: Int,
    val title: String,
    val description: String,
    val books: List<RanobeBook>
)
