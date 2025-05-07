package com.novelight.application.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.novelight.application.data.RoomDataBase.Companion.getDatabase
import com.novelight.application.data.entities.RoomBook
import com.novelight.application.data.entities.RoomBookWithRelease
import com.novelight.application.data.entities.RoomRelease
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieStaffCrossRef
import com.novelight.application.data.entities.RoomSerieWithBooks
import com.novelight.application.data.entities.RoomStaff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RoomRepositori {
    companion object {
        private var repo_database: RoomDataBase? = null

        private var series: List<RoomSerie>? = null

        private var serie: RoomSerie? = null
        private var serieWithBooks: RoomSerieWithBooks? = null

        private fun initializeDB(context: Context): RoomDataBase {
            return RoomDataBase.getDatabase(context)
        }

        fun getSeries(context: Context): List<RoomSerie>? {
            repo_database = initializeDB(context)

            series = repo_database!!.roomSerieDAO().getSeries()

            return series
        }

        fun getSeriesById(context: Context, ids: List<Int>): List<RoomSerie>? {
            repo_database = initializeDB(context)

            series = repo_database!!.roomSerieDAO().getSeriesByIds(ids)

            return series
        }

        fun getFavouriteSeries(context: Context): List<RoomSerie> {
            repo_database = initializeDB(context)

            Log.i("FAVORITAS", repo_database!!.roomSerieDAO().getFavouriteSeries().toString())
            return repo_database!!.roomSerieDAO().getFavouriteSeries()
        }

        suspend fun getFavouriteSeriesTitles(context: Context): List<String> {
            repo_database = initializeDB(context)

            Log.i("FAVORITAS", repo_database!!.roomSerieDAO().getFavouriteSeries().toString())
            return repo_database!!.roomSerieDAO().getFavoriteSerieTitles()
        }

        fun getSerie(context: Context, serieId: Int): RoomSerie? {
            repo_database = initializeDB(context)

            serie = repo_database!!.roomSerieDAO().getSerie(serieId)

            return serie
        }

        fun getRelease(context: Context, releaseId: Int): RoomRelease {
            repo_database = initializeDB(context)

            return repo_database!!.roomReleaseDAO().getRelease(releaseId)
        }

        fun getSerieWithBooks(context: Context, serieId: Int): RoomSerieWithBooks? {
            repo_database = initializeDB(context)

            serieWithBooks = repo_database!!.roomSerieDAO().getSerieWithBooks(serieId)

            return serieWithBooks
        }

        fun getBookWithReleases(context: Context, bookId: Int): RoomBookWithRelease {
            repo_database = initializeDB(context)

            return repo_database!!.roomBookDAO().getBookWithReleases(bookId)
        }

        fun addSerie(context: Context, serie: RoomSerie) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieDAO().insertSerie(serie)
            }
        }

        fun addBook(context: Context, book: RoomBook) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomBookDAO().insertBook(book)
            }
        }

        fun addRelease(context: Context, release: RoomRelease) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomReleaseDAO().addRelease(release)
            }
        }

        fun addStaff(context: Context, staff: RoomStaff) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomStaffDAO().addStaff(staff)
            }
        }

        fun addSerieStaffCrosRef(context: Context, serieStaffCrossRef: RoomSerieStaffCrossRef) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieStaffCrossRefDAO().addSerieStaffCrossRef(serieStaffCrossRef)
            }
        }

        fun updateSerie(context: Context, serie: RoomSerie) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieDAO().updateSerie(
                    serieId = serie.id,
                    title = serie.title,
                    publicationStatus = serie.publication_status,
                    bookDescription = serie.book_description,
                    imageFileName = serie.imageFileName
                )
            }
        }

        fun updateSerieInLibrary(context: Context, serieId: Int, toggle: Boolean) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieDAO().updateSerieInLibrary(serieId, toggle)
            }
        }

        fun updateBook(context: Context, book: RoomBook) {
            repo_database = initializeDB(context)
        }

        fun updateRelease(context: Context, release: RoomRelease) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                val count = repo_database!!.roomReleaseDAO().countRelease(release.id)
                if (count == 0) {
                    addRelease(context, release)
                }
                else {
                    repo_database!!.roomReleaseDAO().updateRelease(
                        releaseId = release.id,
                        title = release.title,
                        romaji = release.romaji.toString()
                    )
                }
            }
        }

        fun updateLastPageRead(context: Context, releaseId:Int, lastPageRead: Int) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomReleaseDAO().updateLastPageRead(releaseId, lastPageRead)
            }
        }

        fun deleteSerie(context: Context, serieId: Int) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieDAO().deleteSerie(serieId)
            }
        }

        suspend fun searchSeriesByTitle(context: Context, query: String): List<RoomSerie> {
            return getDatabase(context).roomSerieDAO().searchFavouriteSeriesByTitle(query)
        }


//        fun addAlumne(alumne: Alumne, context: Context) {
//            repo_database = initializeDB(context)
//
//            CoroutineScope(IO).launch {
//                repo_database!!.alumneDAO().addAlumne(alumne)
//            }
//        }
//
//        fun getAlumnes(context: Context): LiveData<List<Alumne>>? {
//            repo_database = initializeDB(context)
//
//            CoroutineScope(IO).launch {
//                alumnes = repo_database!!.alumneDAO().getAlumnes()
//            }
//
//            return alumnes
//        }
    }
}