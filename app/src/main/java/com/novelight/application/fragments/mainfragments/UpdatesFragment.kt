package com.novelight.application.fragments.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelight.application.adapters.ReleasesAdapter
import com.novelight.application.databinding.FragmentUpdatesBinding
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleaseModel
import com.novelight.application.viewModels.ReleaseViewModel
import kotlinx.coroutines.runBlocking


class UpdatesFragment : Fragment() {

    private lateinit var binding: FragmentUpdatesBinding
    private val releaseViewModel: ReleaseViewModel by viewModels<ReleaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdatesBinding.inflate(inflater, container, false)

        binding.updateRecycler.layoutManager = LinearLayoutManager(context)

        releaseViewModel.releases.observe(viewLifecycleOwner, Observer { releases ->
            updateRecycler(releases)
        })
        Thread({
            runBlocking {
                releaseViewModel.loadSeries(requireContext())
                releaseViewModel.loadReleases(requireContext())
            }
        }).start()


        return binding.root
    }

    private fun updateRecycler(list: List<RanobeReleaseModel>?) {
        if (list != null) binding.updateRecycler.adapter = ReleasesAdapter(list, requireContext())
        else binding.updateRecycler.adapter = ReleasesAdapter(listOf(), requireContext())
        binding.progressBar.setVisibility(View.GONE)
    }

}