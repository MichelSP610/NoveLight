package com.novelight.application.models.apiModels.ranobeDBModels.enums

import com.google.gson.annotations.SerializedName

enum class RanobePublicationStatus(val value: String) {
    @SerializedName("unknown")
    UNKNOWN("Unknown"),

    @SerializedName("ongoing")
    ONGOING("On Going"),

    @SerializedName("completed")
    COMPLETED("Completed"),

    @SerializedName("hiatus")
    HIATUS("Hiatus"),

    @SerializedName("stalled")
    STALLED("Stalled"),

    @SerializedName("cancelled")
    CANCELLED("Cancelled"),
}