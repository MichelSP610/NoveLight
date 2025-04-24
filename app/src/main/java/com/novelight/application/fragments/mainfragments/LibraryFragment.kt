package com.novelight.application.fragments.mainfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.novelight.application.adapters.SeriesAdapter
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.databinding.FragmentLibraryBinding
import com.novelight.application.viewModels.FilterViewModel
import com.novelight.application.viewModels.LibraryViewModel
import com.novelight.application.viewModels.SelectedSerieViewModel
import kotlinx.coroutines.runBlocking

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private val filterViewModel: FilterViewModel by activityViewModels<FilterViewModel>()
    private val selectedSerieViewModel: SelectedSerieViewModel by activityViewModels<SelectedSerieViewModel>()
    private val libraryViewModel: LibraryViewModel by viewModels<LibraryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater)

        binding.libraryRecycler.layoutManager = GridLayoutManager(context, 3)


        //TODO: filter library series by title (not priority)
//        filterViewModel.query.observe(viewLifecycleOwner, Observer { query ->
//            Thread({
//                runBlocking {
//                    serieViewModel.loadSeriesByTitleQuery(requireContext(), query)
//                }
//            }).start()
//        })

        libraryViewModel.librarySeries.observe(viewLifecycleOwner, Observer { series ->
            updateRecycler(series)
        })

        Thread({
            runBlocking {
                libraryViewModel.loadSeries(requireContext())
            }
        }).start()

        return binding.root
    }

    private fun updateRecycler(list: List<RoomSerie>?) {
        if (list != null) {
            binding.libraryRecycler.adapter = SeriesAdapter(list, this, selectedSerieViewModel)
            binding.progressBar.setVisibility(View.GONE)
            binding.libraryRecycler.visibility = View.VISIBLE
        }
        else {
            binding.libraryRecycler.adapter = SeriesAdapter(listOf(), this, selectedSerieViewModel)
            binding.progressBar.setVisibility(View.VISIBLE)
            binding.libraryRecycler.visibility = View.GONE
        }
    }
}