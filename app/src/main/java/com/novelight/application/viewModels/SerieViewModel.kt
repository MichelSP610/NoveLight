package com.novelight.application.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.SupabaseRepositori
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieStaffCrossRef
import com.novelight.application.data.entities.RoomStaff
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.models.apiModels.supabaseModels.SupabaseSerie
import com.novelight.application.utils.CustomUtils

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

    suspend fun loadSeries(context: Context) {
        val dbSerie: List<SupabaseSerie> = SupabaseRepositori.getSeries()
        val ranobeSeries = RanobeRepositori.getSeries(dbSerie.map { it.id })

        insertRoomSerie(context, ranobeSeries)

        val series = RoomRepositori.getSeries(context)

        if (series != null) {
            _series.postValue(series!!)
        }

        Log.i("SERIES", series.toString())
    }

    suspend fun loadSeriesByTitleQuery(context: Context, query: String?) {
        if (query != null && query != "") {
            val dbSerie: List<SupabaseSerie> = SupabaseRepositori.getSeriesByTitle(query)
            Log.i("SERIES BY TITLE", dbSerie.toString())
            val ranobeSeries = RanobeRepositori.getSeries(dbSerie.map { it.id })

            insertRoomSerie(context, ranobeSeries)

            val series = RoomRepositori.getSeries(context)

            if (series != null) {
                _series.postValue(series!!)
            }
        } else {
            loadSeries(context)
        }
    }

    private fun insertRoomSerie(context: Context, series: List<RanobeSerieModel>) {
        series.forEach { ranobeSerie ->
            val roomSerie: RoomSerie = CustomUtils.getRoomSerieFromRanobeSerie(ranobeSerie)
            val roomStaffList: MutableList<RoomStaff> = mutableListOf()
            ranobeSerie.staff.forEach { ranobeStaff ->
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
    }

}