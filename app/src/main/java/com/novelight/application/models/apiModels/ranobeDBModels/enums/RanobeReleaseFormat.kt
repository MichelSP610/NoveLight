package com.novelight.application.models.apiModels.ranobeDBModels.enums

import com.google.gson.annotations.SerializedName

enum class RanobeReleaseFormat(val value: String) {
    @SerializedName("digital")
    DIGITAL("Digital"),

    @SerializedName("print")
    PRINT("Print"),

    @SerializedName("audio")
    AUDIO("Audio"),
}