package com.novelight.application.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.data.entities.RoomBook
import com.novelight.application.data.entities.RoomRelease
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomStaff
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeRelease
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeStaff
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobePublicationStatus
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

        suspend fun getRanobeSerieImageFilename(serie: RanobeSerieModel): String {
            val fullSerie: RanobeSerieModel? = RanobeRepositori.getSerie(serie.id)
            var index: Int? = fullSerie?.books?.lastIndex
            var imageFilename: String? = null

            if (index != null) {
                while (index >= 0 && imageFilename == null) {
                    imageFilename = fullSerie?.books?.get(index)?.image?.filename
                    index--
                }
            }

            return imageFilename.toString()
        }

        fun getRanobeTitleQuery(query: String): String {
            val tempQuery: String = "*" + query + "*"
            return tempQuery.replace("\t", "+")
                .replace("\n", "+")
                .replace(" ", "+")
                .replace("'", "?")
        }

        suspend fun getRoomSerieFromRanobeSerie(ranobeSerie: RanobeSerieModel): RoomSerie {
            val bookDescription: String = ranobeSerie.book_description?.description ?: ""
            val publicationStatus: RanobePublicationStatus = ranobeSerie.publication_status ?: RanobePublicationStatus.UNKNOWN

            return RoomSerie(
                id = ranobeSerie.id,
                title = ranobeSerie.title,
                publication_status = publicationStatus,
                book_description = bookDescription,
                favourite = false,
                imageFileName = getRanobeSerieImageFilename(serie = ranobeSerie)
            )
        }

        fun getRoomBookFromRanobeBook(ranobeBook: RanobeBook, serieId: Int): RoomBook {
            return RoomBook(
                id = ranobeBook.id,
                serieId = serieId,
                title = ranobeBook.title,
                releaseDate = ranobeBook.c_release_date,
                imageFileName = ranobeBook.image.filename,
                language = ranobeBook.lang
            )
        }

        fun getRoomReleaseFromRanobeRelease(ranobeRelease: RanobeRelease, bookId: Int, lastPageRead: Int?) :RoomRelease {
            val lastPage: Int = lastPageRead ?: 0

            return RoomRelease(
                id = ranobeRelease.id,
                bookId = bookId,
                title = ranobeRelease.title,
                romaji = ranobeRelease.romaji,
                release_date = ranobeRelease.release_date,
                pages = ranobeRelease.pages,
                format = ranobeRelease.format,
                lastPageRead = lastPage
            )
        }

        fun getRoomStaffFromRanobeStaff(ranobeStaff: RanobeStaff): RoomStaff {
            return RoomStaff(
                name = ranobeStaff.name,
                roleType = ranobeStaff.role_type,
                romaji = ranobeStaff.romaji
            )
        }
    }
}