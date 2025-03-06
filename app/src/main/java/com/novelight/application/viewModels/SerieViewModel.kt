package com.novelight.application.viewModels

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

    suspend fun loadSeries() {
        val dbSerie: List<SupabaseSerie> = SupabaseRepositori.getSeries()
        Thread({
            _series.postValue(RanobeRepositori.getSeries(dbSerie.map { it.id }));
        }).start()
    }

}