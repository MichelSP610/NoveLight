package com.novelight.application.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.utils.CustomUtils

class SelectedSerieViewModel: ViewModel() {
    private var _selectedSerie: RanobeSerieModel? = null
    val selectedSerie: RanobeSerieModel
        get() = _selectedSerie!!;

    fun setSelectedSerie(serie: RanobeSerieModel) {
        _selectedSerie = serie
        Log.i("SELECTED SERIE", selectedSerie.toString())
    }

    fun addSelectedSerieToLibrary(context: Context) {
        val roomSerie: RoomSerie = CustomUtils.getRoomSerieFromRanobeSerie(selectedSerie)
        RoomRepositori.addSerie(context, roomSerie)
    }

    fun deleteSelectedSerieFromLibrary(context: Context) {
        RoomRepositori.deleteSerie(context, selectedSerie.id)
    }
}