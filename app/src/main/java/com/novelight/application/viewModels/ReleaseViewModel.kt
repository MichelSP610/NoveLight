package com.novelight.application.viewModels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleaseModel
import kotlinx.coroutines.runBlocking

class ReleaseViewModel: ViewModel() {

    private var _releases: MutableLiveData<List<RanobeReleaseModel>> = MutableLiveData()
    val releases: LiveData<List<RanobeReleaseModel>>
        get() = _releases

    private var _librarySeries: MutableLiveData<List<RoomSerie>> = MutableLiveData()
    val librarySeries: LiveData<List<RoomSerie>>
        get() = _librarySeries

    @SuppressLint("SuspiciousIndentation")
    fun loadReleases(context: Context) {
        Thread {
            runBlocking {
                val releasesList: List<RanobeReleaseModel> = RanobeRepositori.getReleasesForFavorites(context)
                val sortedList = releasesList.sortedByDescending { it.release_date }  // Make sure 'date' is correct
                _releases.postValue(sortedList)

            }
        }.start()
    }

    fun loadSeries(context: Context) {
        _librarySeries.postValue(RoomRepositori.getFavouriteSeries(context))
    }
}