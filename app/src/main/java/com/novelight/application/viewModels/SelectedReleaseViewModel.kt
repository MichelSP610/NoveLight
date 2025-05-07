package com.novelight.application.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomRelease

class SelectedReleaseViewModel: ViewModel() {
    private var releaseId: Int = 0
    private val _selectedRelease: MutableLiveData<RoomRelease> = MutableLiveData()
    val selectedRelease: LiveData<RoomRelease>
        get() = _selectedRelease

    fun setSelectedReleaseId(releaseId: Int) {
        this.releaseId = releaseId
    }

    fun loadSelectedRelease(context: Context) {
        val release: RoomRelease = RoomRepositori.getRelease(context, releaseId)
        Log.i("Release", release.toString())
        _selectedRelease.postValue(release)
    }

    fun updateLastPageRead(context: Context, lastPageRead: Int) {
        RoomRepositori.updateLastPageRead(context, releaseId, lastPageRead)
    }
}