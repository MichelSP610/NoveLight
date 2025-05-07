package com.novelight.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.novelight.application.R
import com.novelight.application.data.entities.RoomRelease
import com.novelight.application.databinding.FragmentReleaseBinding
import com.novelight.application.viewModels.SelectedReleaseViewModel

class ReleaseFragment : Fragment() {
    private lateinit var binding: FragmentReleaseBinding
    private val selectedReleaseViewModel: SelectedReleaseViewModel by activityViewModels<SelectedReleaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReleaseBinding.inflate(inflater)

        Thread {
            selectedReleaseViewModel.loadSelectedRelease(requireContext())
        }.start()

        selectedReleaseViewModel.selectedRelease.observe(viewLifecycleOwner, Observer {roomRelease ->
            updateUI(roomRelease)
        })

        binding.updateButton.setOnClickListener {
            Thread {
                selectedReleaseViewModel.updateLastPageRead(
                    requireContext(),
                    binding.lastPageReadInput.text.toString().toInt()
                )
            }.start()
        }

        return binding.root
    }

    fun updateUI(roomRelease: RoomRelease) {
        binding.lastPageReadInput.setText(roomRelease.lastPageRead.toString())
        binding.totalPagesTextView.text = roomRelease.pages.toString()
    }

}