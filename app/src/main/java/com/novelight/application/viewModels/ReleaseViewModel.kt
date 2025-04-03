package com.novelight.application.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleaseModel

class ReleaseViewModel: ViewModel() {

    private var _releases: MutableLiveData<List<RanobeReleaseModel>> = MutableLiveData()
    val releases: LiveData<List<RanobeReleaseModel>>
        get() = _releases

    @SuppressLint("SuspiciousIndentation")
    fun loadReleases() {
        val releasesList: List<RanobeReleaseModel> = RanobeRepositori.getReleases()
        _releases.postValue(releasesList)
    }
}