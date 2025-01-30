package com.novelight.application.models

import android.util.Log
import android.widget.SearchView

class MyOnQueryTextListener: SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("Caracola", query.toString())
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        Log.d("adios", query.toString())
        return false
    }
}