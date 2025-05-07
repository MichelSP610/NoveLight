package com.novelight.application.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieStaffCrossRef
import com.novelight.application.data.entities.RoomStaff
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.utils.CustomUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SerieViewModel: ViewModel() {
    private var _series: MutableLiveData<List<RoomSerie>> = MutableLiveData()
    val series: LiveData<List<RoomSerie>>
        get() = _series

    fun clearSeries() {
        if (series.value == null || series.value!!.isEmpty()) {
            _series = MutableLiveData()
        }
    }

    fun isSeriesEmpty(): Boolean {
        return (series.value == null || series.value!!.isEmpty())
    }

     fun loadSeries(context: Context) {
        val ranobeSeries = RanobeRepositori.getSeries()

        insertRoomSeries(context, ranobeSeries)

        val series = RoomRepositori.getSeriesById(context, ranobeSeries.map { it.id })

        if (series != null) {
            _series.postValue(series!!)
        }
    }

    //TODO: CHANGE QUERY TO SEARCH IN API
    suspend fun loadSeriesByTitleQuery(context: Context, query: String?) {
        if (query != null && query != "") {
            val ranobeSeries = RanobeRepositori.getSeries()

            insertRoomSeries(context, ranobeSeries)

            val series = RoomRepositori.getSeries(context)

            if (series != null) {
                _series.postValue(series!!)
            }
        } else {
            loadSeries(context)
        }
    }

    private fun insertRoomSeries(context: Context, series: List<RanobeSerieModel>) {
        val threadList: MutableList<Thread> = mutableListOf()

        series.forEach { ranobeSerie ->
            val addSerieThread: Thread = Thread {
                runBlocking {
                    addSerie(context, ranobeSerie)
                }
            }

            threadList.add(addSerieThread)
            addSerieThread.start()
            Thread.sleep(50)
        }

        threadList.forEach {
            it.join()
        }
    }

    private suspend fun addSerie(context: Context, ranobeSerie: RanobeSerieModel) {
        val roomSerie: RoomSerie = CustomUtils.getRoomSerieFromRanobeSerie(ranobeSerie)
        val roomStaffList: MutableList<RoomStaff> = mutableListOf()
        ranobeSerie.staff?.forEach { ranobeStaff ->
            roomStaffList.add(CustomUtils.getRoomStaffFromRanobeStaff(ranobeStaff))
        }
        RoomRepositori.addSerie(context, roomSerie)
        roomStaffList.forEach { roomStaff ->
            RoomRepositori.addStaff(context, roomStaff)
            RoomRepositori.addSerieStaffCrosRef(
                context,
                RoomSerieStaffCrossRef(roomSerie.id, roomStaff.name)
            )
        }
    }

    fun loadSeriesFromApi(context: Context, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val results = RanobeRepositori.getSeriesByTitle(query)
            Log.i("ExploreSearch", "API returned ${results.size} results")

            val threadList = mutableListOf<Thread>()

            results.forEach { serie ->
                val thread = Thread {
                    runBlocking {
                        val fullSerie = RanobeRepositori.getSerie(serie.id)

                        val firstBookId = fullSerie?.books?.firstOrNull()?.id
                        val imageFileName = if (firstBookId != null) {
                            RanobeRepositori.getBook(firstBookId)?.image?.filename ?: ""
                        } else ""

                        Log.i("ImageFetch", "Series: ${fullSerie?.title} → Image: $imageFileName")

                        val roomSerie = fullSerie?.publication_status?.let {
                            RoomSerie(
                                id = fullSerie.id,
                                title = fullSerie.title,
                                publication_status = it,
                                book_description = (fullSerie.book_description ?: "").toString(),
                                imageFileName = imageFileName,
                                favourite = false
                            )
                        }

                        if (roomSerie != null) {
                            RoomRepositori.addSerie(context, roomSerie)
                        }
                    }
                }

                thread.start()
                threadList.add(thread)
                Thread.sleep(50) // optional, avoids API burst
            }

            threadList.forEach { it.join() }

            val filtered = RoomRepositori.getSeriesById(context, results.map { it.id }) ?: emptyList()
            _series.postValue(filtered)
        }
    }





}