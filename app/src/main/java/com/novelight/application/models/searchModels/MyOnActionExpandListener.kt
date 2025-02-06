package com.novelight.application.models.searchModels

import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.widget.SearchView

class MyOnActionExpandListener(private val searchView: SearchView) : OnActionExpandListener {
    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        searchView.onActionViewExpanded()
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        searchView.onActionViewCollapsed()
        searchView.setQuery("", true)
        return true
    }

}