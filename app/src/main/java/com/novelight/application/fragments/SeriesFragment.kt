package com.novelight.application.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelight.application.R
import com.novelight.application.adapters.SerieBooksAdapter
import com.novelight.application.data.entities.RoomSerieWithBooks
import com.novelight.application.data.entities.RoomStaff
import com.novelight.application.databinding.FragmentSeriesBinding
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobePublicationStatus
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeStaffRoleType
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedBookViewModel
import com.novelight.application.viewModels.SelectedSerieViewModel

class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private val selectedSerieViewModel: SelectedSerieViewModel by activityViewModels<SelectedSerieViewModel>()
    private val selectedBookViewModel: SelectedBookViewModel by activityViewModels<SelectedBookViewModel>()

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
            binding.serieBooksRecycler.adapter = SerieBooksAdapter(selectedSerie.books, findNavController(), selectedBookViewModel)
            binding.serieTitle.text = selectedSerie.serie.title

            if (!selectedSerie.staff.isEmpty()) {
                val author: RoomStaff? = selectedSerie.staff.find {
                    it.roleType == RanobeStaffRoleType.AUTHOR
                }

                if (author?.romaji != null) {binding.serieAuthorName.text = author.romaji}
                else {binding.serieAuthorName.text = author?.name}

                val artist: RoomStaff? = selectedSerie.staff.find {
                    it.roleType == RanobeStaffRoleType.ARTIST
                }

                if (artist?.romaji != null) {binding.serieArtistName.text = artist.romaji}
                else {binding.serieArtistName.text = artist?.name}
            } else {
                binding.serieAuthorName.text = "Author"
                binding.serieArtistName.text = "Artist"
            }

            binding.serieStatusName.text = selectedSerie.serie.publication_status.value

            binding.serieDescription.text = selectedSerie.serie.book_description

            binding.serieVolumeCount.text = selectedSerie.books.size.toString() + " Books"

            CustomUtils.loadRanobeImageOnImageView(
                binding.serieImage,
                selectedSerie.serie.imageFileName,
                requireContext()
            )

            val statusEnum = selectedSerie.serie.publication_status
            binding.serieStatusName.text = statusEnum.value


            val iconRes = when (statusEnum) {
                RanobePublicationStatus.ONGOING -> R.drawable.ongoingicon
                RanobePublicationStatus.COMPLETED -> R.drawable.completedicon
                RanobePublicationStatus.HIATUS -> R.drawable.hiatusicon
                RanobePublicationStatus.CANCELLED -> R.drawable.cancelledicon
                RanobePublicationStatus.STALLED -> R.drawable.unknownicon
                RanobePublicationStatus.UNKNOWN -> R.drawable.unknownicon
            }

            binding.serieStatusIcon.setImageResource(iconRes)

            binding.serieLibraryToggleButton.isChecked = selectedSerie.serie.favourite

            binding.progressBar.setVisibility(View.GONE)
            binding.nestedScrollView.setVisibility(View.VISIBLE)
        }
    }
}