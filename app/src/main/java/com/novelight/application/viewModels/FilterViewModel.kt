package com.novelight.application.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilterViewModel : ViewModel() {
    private val _query: MutableLiveData<String> = MutableLiveData("")
    val query: LiveData<String>
        get() = _query

    fun setQuery(searchQuery : String) {
        _query.value = searchQuery
    }
}