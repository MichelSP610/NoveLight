package com.novelight.application.viewModels

import androidx.lifecycle.ViewModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel

class SelectedSerieViewModel: ViewModel() {
    private var _selectedSerie: RanobeSerieModel? = null
    val selectedSerie: RanobeSerieModel
        get() = _selectedSerie!!;

    fun setSelectedSerie(serie: RanobeSerieModel) {
        _selectedSerie = serie
    }
}