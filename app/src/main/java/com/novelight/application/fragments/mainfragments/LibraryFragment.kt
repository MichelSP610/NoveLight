package com.novelight.application.fragments.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.novelight.application.R
import com.novelight.application.databinding.FragmentLibraryBinding
import com.novelight.application.viewModels.FilterViewModel

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private val filterViewModel: FilterViewModel by activityViewModels<FilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater)

        filterViewModel.query.observe(viewLifecycleOwner) { query ->
            binding.libraryTestTextView.text = query.toString()
        }

        return binding.root
    }
}