package com.novelight.application.fragments.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.novelight.application.R
import com.novelight.application.databinding.FragmentConfigBinding


class ConfigFragment : Fragment() {



    private lateinit var binding: FragmentConfigBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentConfigBinding.inflate(inflater, container, false)


        binding.usuari.setOnClickListener{
            findNavController().navigate(R.id.action_configFragment3_to_configUserFragment2)
        }

        binding.about.setOnClickListener{
            findNavController().navigate(R.id.action_configFragment3_to_configAboutFragment2)
        }

        return binding.root
    }


}