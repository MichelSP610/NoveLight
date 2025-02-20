package com.novelight.application.utils

class CustomUtils {
    companion object {
         fun checkEmailFormat(email: String): Boolean {
            val regex = Regex(
                pattern = ".+@[a-zA-z]+\\.[a-zA-Z]+",
                options = setOf(RegexOption.IGNORE_CASE)
            )

            if (regex.matches(email)) {
                return true;
            }

            throw Exception("Email format is not correct")
        }
    }
}