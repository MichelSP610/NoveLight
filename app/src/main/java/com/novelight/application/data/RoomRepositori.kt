package com.novelight.application.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieWithBooks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RoomRepositori {
    companion object {
        private var repo_database: RoomDataBase? = null

        private var series: LiveData<List<RoomSerie>>? = null

        private var serie: LiveData<RoomSerie>? = null
        private var serieWithBooks: LiveData<RoomSerieWithBooks>? = null

        private fun initializeDB(context: Context): RoomDataBase {
            return RoomDataBase.getDatabase(context)
        }

        fun getSeries(context: Context): LiveData<List<RoomSerie>>? {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                series = repo_database!!.roomSerieDAO().getSeries()
            }

            return series
        }

        fun getSerie(context: Context, serieId: Int): LiveData<RoomSerie>? {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                serie = repo_database!!.roomSerieDAO().getSerie(serieId)
            }

            return serie
        }

        fun getSerieWithBooks(context: Context, serieId: Int): LiveData<RoomSerieWithBooks>? {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                serieWithBooks = repo_database!!.roomSerieDAO().getSerieWithBooks(serieId)
            }

            return serieWithBooks
        }

        fun addSerie(context: Context, serie: RoomSerie) {
            repo_database = initializeDB(context)

            CoroutineScope(IO).launch {
                repo_database!!.roomSerieDAO().insertSerie(serie)
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