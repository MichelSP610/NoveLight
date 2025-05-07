package com.novelight.application.fragments.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.novelight.application.R
import com.novelight.application.databinding.FragmentConfigAboutBinding
import com.novelight.application.databinding.FragmentConfigBinding


class ConfigAboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentConfigAboutBinding.inflate(inflater, container, false)

        binding.textView5.setOnClickListener {
            if (binding.privacyText.visibility == View.GONE) {
                binding.privacyText.visibility = View.VISIBLE
            } else {
                binding.privacyText.visibility = View.GONE
            }
        }

        return binding.root
    }


}