package com.novelight.application.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kermit.Logger
import com.novelight.application.R
import com.novelight.application.adapters.BooksAdapter
import com.novelight.application.databinding.FragmentSeriesBinding
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeStaff
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeStaffRoleType
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedSerieViewModel

class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private val selectedSerieViewModel: SelectedSerieViewModel by activityViewModels<SelectedSerieViewModel>()

    //TODO images for author, artist and status need to be added
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(inflater)

        binding.serieBooksRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.serieBooksRecycler.adapter =
            BooksAdapter(selectedSerieViewModel.selectedSerie.books)

        binding.serieBooksRecycler.setHasFixedSize(false)

        binding.serieTitle.text = selectedSerieViewModel.selectedSerie.title

        val author: RanobeStaff = selectedSerieViewModel.selectedSerie.staff.find {
            it.role_type == RanobeStaffRoleType.AUTHOR
        }!!

        if (author.romaji != null) {binding.serieAuthorName.text = author.romaji}
        else {binding.serieAuthorName.text = author.name}

        val artist: RanobeStaff = selectedSerieViewModel.selectedSerie.staff.find {
            it.role_type == RanobeStaffRoleType.ARTIST
        }!!

        if (artist.romaji != null) {binding.serieArtistName.text = artist.romaji}
        else {binding.serieArtistName.text = artist.name}

        binding.serieStatusName.text = selectedSerieViewModel.selectedSerie.publication_status.value

        binding.serieDescription.text = selectedSerieViewModel.selectedSerie.book_description.description

        binding.serieVolumeCount.text = selectedSerieViewModel.selectedSerie.books.size.toString() + " Books"

        CustomUtils.loadRanobeImageOnImageView(
            binding.serieImage,
            CustomUtils.getRanobeSerieImageFilename(selectedSerieViewModel.selectedSerie),
            requireContext()
        )

        return binding.root
    }
}