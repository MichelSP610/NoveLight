package com.novelight.application.models.apiModels.ranobeDBModels.enums

import com.google.gson.annotations.SerializedName

enum class RanobeStaffRoleType(s: String) {
    @SerializedName("staff")
    STAFF("staff"),

    @SerializedName("author")
    AUTHOR("author"),

    @SerializedName("artist")
    ARTIST("artist"),

    @SerializedName("editor")
    EDITOR("editor"),

    @SerializedName("translator")
    TRANSLATOR("translator"),

    @SerializedName("narrator")
    NARRATOR("narrator")
}