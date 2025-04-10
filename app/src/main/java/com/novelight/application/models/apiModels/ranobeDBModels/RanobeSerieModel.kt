package com.novelight.application.models.apiModels.ranobeDBModels

import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobePublicationStatus

data class RanobeSerieModel(
    val id: Int,
    val title: String,
    val publication_status: RanobePublicationStatus,
    val book_description: RanobeBookDescription,
    val books: List<RanobeBook>,
    val staff: List<RanobeStaff>
)
