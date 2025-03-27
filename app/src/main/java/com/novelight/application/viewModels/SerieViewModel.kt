package com.novelight.application.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.repositoris.RanobeRepositori
import com.novelight.application.data.repositoris.SupabaseRepositori
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.models.apiModels.supabaseModels.SupabaseSerie

class SerieViewModel: ViewModel() {
    private var _series: MutableLiveData<List<RanobeSerieModel>> = MutableLiveData()
    val series: LiveData<List<RanobeSerieModel>>
        get() = _series

    fun clearSeries() {
        if (series.value == null || series.value!!.isEmpty()) {
            _series.postValue(listOf())
        }
    }

    fun isSeriesEmpty(): Boolean {
        return (series.value == null || series.value!!.isEmpty())
    }

    suspend fun loadSeries() {
        val dbSerie: List<SupabaseSerie> = SupabaseRepositori.getSeries()
        _series.postValue(RanobeRepositori.getSeries(dbSerie.map { it.id }));
    }

    suspend fun loadSeriesByTitleQuery(query: String?) {
        if (query != null && query != "") {
            val dbSerie: List<SupabaseSerie> = SupabaseRepositori.getSeriesByTitle(query)
            Log.i("SERIES BY TITLE", dbSerie.toString())
            _series.postValue(RanobeRepositori.getSeries(dbSerie.map { it.id }))
        } else {
            loadSeries()
        }
    }

}