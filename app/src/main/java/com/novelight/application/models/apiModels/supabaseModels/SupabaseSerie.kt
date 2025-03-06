package com.novelight.application.models.apiModels.supabaseModels

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseSerie(
    val id: Int,
    val title: String
)
