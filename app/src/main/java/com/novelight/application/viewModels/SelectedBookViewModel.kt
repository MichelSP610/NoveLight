package com.novelight.application.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomBookWithRelease
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeBook
import com.novelight.application.utils.CustomUtils

class SelectedBookViewModel: ViewModel() {
    private var selectedBookId: Int = 0

    private val _selectedBook: MutableLiveData<RoomBookWithRelease> = MutableLiveData()
    val selectedBook: LiveData<RoomBookWithRelease>
        get() = _selectedBook

    fun setSelectedBookId(bookId: Int) {
        selectedBookId = bookId
    }

    fun loadSelectedBook(context: Context) {
        loadBook(context)
        Thread {
            updateSelectedBook(context)
        }.start()
    }

    private fun loadBook(context: Context) {
        _selectedBook.postValue(RoomRepositori.getBookWithReleases(context, selectedBookId))
    }

    private fun updateSelectedBook(context: Context) {
        val ranobeBook: RanobeBook? = RanobeRepositori.getBook(selectedBookId)
        ranobeBook.let {
            if (ranobeBook!!.id != 0) {
                RoomRepositori.updateBook(context, CustomUtils.getRoomBookFromRanobeBook(ranobeBook, 0))
                ranobeBook.releases.let {
                    ranobeBook.releases!!.forEach {
                        RoomRepositori.updateRelease(context, CustomUtils.getRoomReleaseFromRanobeRelease(it, selectedBookId))
                    }
                }
                loadBook(context)
            }
        }
    }
}