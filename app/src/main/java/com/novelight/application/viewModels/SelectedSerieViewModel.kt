package com.novelight.application.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novelight.application.data.RanobeRepositori
import com.novelight.application.data.RoomRepositori
import com.novelight.application.data.entities.RoomBook
import com.novelight.application.data.entities.RoomRelease
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieWithBooks
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.utils.CustomUtils

class SelectedSerieViewModel: ViewModel() {
    private var _selectedSerieId: Int = 0
    val selectedSerieId :Int
        get() = _selectedSerieId

    private var _selectedSerie: MutableLiveData<RoomSerieWithBooks?> = MutableLiveData()
    val selectedSerie: LiveData<RoomSerieWithBooks?>
        get() = _selectedSerie;

    fun setSelectedSerieId(serieId: Int) {
        _selectedSerieId = serieId
    }

    //TODO: this function has to be changed so the update is done in another thread and the serie loads faster
    fun loadSelectedSerie(context: Context) {
        _selectedSerie.postValue(null)
        val ranobeSerie: RanobeSerieModel? = RanobeRepositori.getSerie(id = selectedSerieId)
        ranobeSerie?.books?.forEach { book ->
            val ranobeBook: RanobeBook? = RanobeRepositori.getBook(book.id)
            val roomBook: RoomBook = CustomUtils.getRoomBookFromRanobeBook(book, ranobeSerie.id)
            RoomRepositori.addBook(context, roomBook)
            ranobeBook?.releases?.forEach { release ->
                val roomRelease: RoomRelease = CustomUtils.getRoomReleaseFromRanobeRelease(release, ranobeBook.id)
                RoomRepositori.addRelease(context, roomRelease)
            }
        }
        Log.i("SERIE ID SHOULD RETRIEVE", selectedSerieId.toString())
        val roomSerie: RoomSerie? = RoomRepositori.getSerie(context, selectedSerieId)
        val completeSerie: RoomSerieWithBooks? = RoomRepositori.getSerieWithBooks(context, selectedSerieId)
        Log.i("SELECTED SERIE", roomSerie.toString())
        Log.i("SELECTED COMPLETE SERIE", completeSerie.toString())
        if (completeSerie != null) {
            _selectedSerie.postValue(completeSerie)
        }
    }

    fun updateSelectedSerieInLibrary(context: Context, toggle: Boolean) {
        RoomRepositori.updateSerieInLibrary(context, selectedSerieId, toggle)
    }

    fun deleteSelectedSerieFromLibrary(context: Context) {
        RoomRepositori.deleteSerie(context, selectedSerieId)
    }
}