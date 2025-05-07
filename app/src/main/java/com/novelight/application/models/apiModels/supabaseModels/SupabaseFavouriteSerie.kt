package com.novelight.application.models.apiModels.supabaseModels

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseFavouriteSerie(
    val id: Int?,
    val serie_id: Int
)
