package com.novelight.application.fragments.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.novelight.application.R
import com.novelight.application.data.SupabaseRepositori
import com.novelight.application.databinding.FragmentConfigBinding
import com.novelight.application.databinding.FragmentConfigUserBinding


class ConfigUserFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentConfigUserBinding.inflate(inflater, container, false)

        val spinner: Spinner = binding.spinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SyncData_Array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.buttonUserLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_configUserFragment2_to_loginFragment)
        }

        binding.buttonUserSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_configUserFragment2_to_registerFragment)
        }

        binding.button5.setOnClickListener {
            SupabaseRepositori.updateData(requireContext());
        }

        return binding.root
    }

}