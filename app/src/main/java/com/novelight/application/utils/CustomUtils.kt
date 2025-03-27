package com.novelight.application.utils

import android.content.Context
import android.widget.ImageView
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date

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

        fun getFormattedDateString(date: Date): String {
            val format = SimpleDateFormat("dd/MM/yyyy").format(date)
            return format
        }

        fun loadRanobeImageOnImageView(imageView: ImageView, imageFilename: String, context: Context) {
            Picasso.with(context)
                .load("https://images.ranobedb.org/" + imageFilename)
                .into(imageView)
        }

        fun getRanobeSerieImageFilename(serie: RanobeSerieModel): String {
            return serie.books.get(serie.books.lastIndex).image.filename
        }

        fun getRanobeTitleQuery(query: String): String {
            val tempQuery: String = "*" + query + "*"
            return tempQuery.replace("\t", "+")
                .replace("\n", "+")
                .replace(" ", "+")
                .replace("'", "?")
        }
    }
}