package com.novelight.application.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelight.application.R
import com.novelight.application.adapters.BookReleasesAdapter
import com.novelight.application.adapters.SerieBooksAdapter
import com.novelight.application.data.entities.RoomBookWithRelease
import com.novelight.application.data.entities.RoomSerieWithBooks
import com.novelight.application.data.entities.RoomStaff
import com.novelight.application.databinding.FragmentBookBinding
import com.novelight.application.databinding.FragmentSeriesBinding
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeReleaseFormat
import com.novelight.application.models.apiModels.ranobeDBModels.enums.RanobeStaffRoleType
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.SelectedBookViewModel
import com.novelight.application.viewModels.SelectedSerieViewModel

class BookFragment : Fragment() {

    private lateinit var binding: FragmentBookBinding
    private val selectedBookViewModel: SelectedBookViewModel by activityViewModels<SelectedBookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookBinding.inflate(inflater)

        binding.bookReleasesRecycler.layoutManager = LinearLayoutManager(requireContext())

        Thread({
            selectedBookViewModel.loadSelectedBook(requireContext())
        }).start()

        selectedBookViewModel.selectedBook.observe(viewLifecycleOwner, Observer { selectedSerie ->
            updateView(selectedSerie)
        })

        binding.bookReleasesRecycler.setHasFixedSize(false)

        return binding.root
    }

    private fun updateView(selectedBook: RoomBookWithRelease?) {
        if (selectedBook != null) {
            binding.bookReleasesRecycler.adapter = BookReleasesAdapter(
                selectedBook.releases.filter {
                    it.format == RanobeReleaseFormat.PRINT || it.format == RanobeReleaseFormat.DIGITAL
                }.sortedByDescending { it.release_date }
            )

            binding.bookTitle.text = selectedBook.book.title

            //TODO: get book description
//            binding.bookDescription.text = selectedBook.book.

            binding.bookReleaseCount.text = selectedBook.releases.size.toString() + " Releases"

            CustomUtils.loadRanobeImageOnImageView(
                binding.bookImage,
                selectedBook.book.imageFileName,
                requireContext()
            )
        }
    }
}