package com.novelight.application.models.apiModels.supabaseModels

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseReadRelease(
    val id: Int?,
    val book_id: Int,
    val release_id: Int,
    val last_page_read: Int
)
