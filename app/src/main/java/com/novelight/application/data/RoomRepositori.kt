package com.novelight.application.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.novelight.application.data.entities.RoomBook
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

        fun getSerie(context: Context, serieId: Int): RoomSerie? {
            repo_database = initializeDB(context)

            serie = repo_database!!.roomSerieDAO().getSerie(serieId)

            return serie
        }

        fun getSerieWithBooks(context: Context, serieId: Int): RoomSerieWithBooks? {
            repo_database = initializeDB(context)

            serieWithBooks = repo_database!!.roomSerieDAO().getSerieWithBooks(serieId)

            return serieWithBooks
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

        fun deleteSerie(context: Context, serieId: Int) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieDAO().deleteSerie(serieId)
            }
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