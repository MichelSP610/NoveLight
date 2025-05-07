package com.novelight.application.models.searchModels

import android.widget.SearchView
import com.novelight.application.viewModels.FilterViewModel

class MyOnQueryTextListener(private val viewModel: FilterViewModel): SearchView.OnQueryTextListener {

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        viewModel.setQuery(query.toString())
//        return false
//    }
//
//    override fun onQueryTextChange(query: String?): Boolean {
//        if (query == null || query == "") {
//            viewModel.setQuery(query.toString())
//        }
//        return false
//    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            viewModel.setQuery(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        // Don't run search on every key stroke
        return false
    }
}