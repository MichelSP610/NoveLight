package com.novelight.application.fragments.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelight.application.R
import com.novelight.application.adapters.SeriesAdapter
import com.novelight.application.databinding.FragmentExploreBinding
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.viewModels.SerieViewModel
import kotlinx.coroutines.runBlocking

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private val serieViewModel: SerieViewModel by viewModels<SerieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater)

        binding.exploreRecycler.layoutManager = GridLayoutManager(context, 2)

        serieViewModel.series.observe(viewLifecycleOwner, Observer { series ->
            updateRecycler(series)
        })

        runBlocking {
            serieViewModel.loadSeries()
        }

        return binding.root
    }

    private fun updateRecycler(list: List<RanobeSerieModel>?) {
        if (list != null) binding.exploreRecycler.adapter = SeriesAdapter(list, requireContext())
        else binding.exploreRecycler.adapter = SeriesAdapter(listOf(), requireContext())
    }
}