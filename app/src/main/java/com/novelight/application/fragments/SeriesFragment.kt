package com.novelight.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelight.application.adapters.BooksAdapter
import com.novelight.application.data.entities.RoomSerieWithBooks
import com.novelight.application.data.entities.RoomStaff
import com.novelight.application.databinding.FragmentSeriesBinding
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeStaff
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeStaffRoleType
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedSerieViewModel

class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private val selectedSerieViewModel: SelectedSerieViewModel by activityViewModels<SelectedSerieViewModel>()

    //TODO: images for author, artist and status need to be added
    //TODO: make the fragment not interactable while loading
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(inflater)

        binding.serieBooksRecycler.layoutManager = LinearLayoutManager(requireContext())

        Thread({
            selectedSerieViewModel.loadSelectedSerie(requireContext())
        }).start()

        selectedSerieViewModel.selectedSerie.observe(viewLifecycleOwner, Observer {selectedSerie ->
            updateView(selectedSerie)
        })

        binding.serieLibraryToggleButton.setOnClickListener {
            selectedSerieViewModel.updateSelectedSerieInLibrary(
                requireContext(),
                binding.serieLibraryToggleButton.isChecked
            )
        }

        binding.serieBooksRecycler.setHasFixedSize(false)

        return binding.root
    }

    private fun updateView(selectedSerie: RoomSerieWithBooks?) {
        if (selectedSerie != null) {
            binding.serieBooksRecycler.adapter = BooksAdapter(selectedSerie.books)
            binding.serieTitle.text = selectedSerie.serie.title

            val author: RoomStaff = selectedSerie.staff.find {
                it.roleType == RanobeStaffRoleType.AUTHOR
            }!!

            if (author.romaji != null) {binding.serieAuthorName.text = author.romaji}
            else {binding.serieAuthorName.text = author.name}

            val artist: RoomStaff = selectedSerie.staff.find {
                it.roleType == RanobeStaffRoleType.ARTIST
            }!!

            if (artist.romaji != null) {binding.serieArtistName.text = artist.romaji}
            else {binding.serieArtistName.text = artist.name}

            binding.serieStatusName.text = selectedSerie.serie.publication_status.value

            binding.serieDescription.text = selectedSerie.serie.book_description

            binding.serieVolumeCount.text = selectedSerie.books.size.toString() + " Books"

            CustomUtils.loadRanobeImageOnImageView(
                binding.serieImage,
                selectedSerie.serie.imageFileName,
                requireContext()
            )

            binding.serieLibraryToggleButton.isChecked = selectedSerie.serie.favourite
        }
    }
}