package com.novelight.application.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomSerie

class LibraryViewModel: ViewModel() {
    private var _librarySeries: MutableLiveData<List<RoomSerie>> = MutableLiveData()
    val librarySeries: LiveData<List<RoomSerie>>
        get() = _librarySeries

    fun loadSeries(context: Context) {
        _librarySeries.postValue(RoomRepositori.getFavouriteSeries(context))
    }
}