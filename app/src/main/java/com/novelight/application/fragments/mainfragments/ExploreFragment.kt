package com.novelight.application.fragments.mainfragments

import android.os.Bundle
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
import com.novelight.application.databinding.FragmentExploreBinding
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.viewModels.FilterViewModel
import com.novelight.application.viewModels.SelectedSerieViewModel
import com.novelight.application.viewModels.SerieViewModel
import kotlinx.coroutines.runBlocking

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private val serieViewModel: SerieViewModel by viewModels<SerieViewModel>()
    private val selectedSerieViewModel: SelectedSerieViewModel by activityViewModels<SelectedSerieViewModel>()
    private val filterViewModel: FilterViewModel by activityViewModels<FilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater)

        binding.exploreRecycler.layoutManager = GridLayoutManager(context, 3)

        filterViewModel.query.observe(viewLifecycleOwner) { query ->
            serieViewModel.loadSeriesFromApi(requireContext(), query)
        }


        serieViewModel.series.observe(viewLifecycleOwner, Observer { series ->
            updateRecycler(series)
        })


        Thread({
            runBlocking {
                serieViewModel.loadSeries(requireContext())
            }
        }).start()

        return binding.root
    }

    private fun updateRecycler(list: List<RoomSerie>?) {
        if (list != null) {
            binding.exploreRecycler.adapter = SeriesAdapter(list, this, selectedSerieViewModel)
            binding.progressBar.setVisibility(View.GONE)
            binding.exploreRecycler.visibility = View.VISIBLE
        }
        else {
            binding.exploreRecycler.adapter = SeriesAdapter(listOf(), this, selectedSerieViewModel)
            binding.progressBar.setVisibility(View.VISIBLE)
            binding.exploreRecycler.visibility = View.GONE
        }
        binding.progressBar.setVisibility(View.GONE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filterViewModel.setQuery("") // Reset the shared search query when leaving Explore
    }


}