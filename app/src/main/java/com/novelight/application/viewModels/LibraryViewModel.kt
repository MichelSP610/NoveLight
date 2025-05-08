package com.novelight.application.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomSerie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryViewModel: ViewModel() {
    private var _librarySeries: MutableLiveData<List<RoomSerie>> = MutableLiveData()
    val librarySeries: LiveData<List<RoomSerie>>
        get() = _librarySeries

    private val _filteredSeries = MutableLiveData<List<RoomSerie>>()
    val filteredSeries: LiveData<List<RoomSerie>> get() = _filteredSeries

    fun loadSeries(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = RoomRepositori.getFavouriteSeries(context)
            _librarySeries.postValue(result)
        }
    }


    fun searchSeriesByTitle(context: Context, query: String) {
        viewModelScope.launch {
            val results = RoomRepositori.searchSeriesByTitle(context, query)
            _filteredSeries.postValue(results)
        }
    }

    fun refreshLibrary(context: Context) {
        viewModelScope.launch {
            val updatedFavorites = withContext(Dispatchers.IO) {
                RoomRepositori.getFavouriteSeries(context)
            }
            _librarySeries.postValue(updatedFavorites)
        }
    }

}