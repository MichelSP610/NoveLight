package com.novelight.application.models.apiModels.ranobeDBModels.enums

import com.google.gson.annotations.SerializedName

enum class RanobeReleaseFormat(val value: String) {
    @SerializedName("digital")
    DIGITAL("digital"),

    @SerializedName("print")
    PRINT("print"),

    @SerializedName("audio")
    AUDIO("audio"),
}