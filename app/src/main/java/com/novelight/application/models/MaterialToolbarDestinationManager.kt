package com.novelight.application.models

import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.appbar.MaterialToolbar
import com.novelight.application.R
import com.novelight.application.models.searchModels.MyOnActionExpandListener
import com.novelight.application.models.searchModels.MyOnQueryTextListener
import com.novelight.application.viewModels.FilterViewModel

class MaterialToolbarDestinationManager(
    private val materialToolbar: MaterialToolbar,
    private val navController: NavController,
    private val filterViewModel: FilterViewModel
) {

    init {
        setToolBarMenuOptions()
    }

    fun onFragmentChange(destination: NavDestination) {
        closeLibrarySearchViewIfExpanded()
        showToolBarGroup(destination.id)
    }

    private fun goToSettingsFragment(actionId: Int) {
        showToolBarGroup(null)
        navController.navigate(actionId)
    }

    private fun setupLibraryToolBarSearchView() {
        val searchMenuItem = materialToolbar.menu.findItem(R.id.librarySearch)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(MyOnQueryTextListener(filterViewModel))
        searchView.isIconifiedByDefault = false

        val searchCloseButtonId =
            searchView.context.resources.getIdentifier("android:id/search_close_btn", null, null)
        val closeButton = searchView.findViewById<ImageView>(searchCloseButtonId)
        closeButton.setImageResource(R.drawable.blank)
        closeButton.isEnabled = false

        searchMenuItem.setOnActionExpandListener(MyOnActionExpandListener(searchView))
    }

    private fun closeLibrarySearchViewIfExpanded() {
        val searchMenuItem = materialToolbar.menu.findItem(R.id.librarySearch)
        if (searchMenuItem.isActionViewExpanded) {
            searchMenuItem.collapseActionView()
        }
    }

    private fun setToolBarMenuOptions() {
        setupLibraryToolBarSearchView()

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                // libraryMenu
                R.id.librarySettings -> {
                    goToSettingsFragment(R.id.action_libraryFragment_to_configFragment3)
                    true
                }

                // updatesMenu
                R.id.updatesTest -> {
                    goToSettingsFragment(R.id.action_updatesFragment_to_configFragment3)
                    true
                }

                R.id.updatesSettings -> {
                    goToSettingsFragment(R.id.action_updatesFragment_to_configFragment3)
                    true
                }

                // historyMenu
                R.id.historyTest -> {
                    true
                }

                R.id.historySettings -> {
                    goToSettingsFragment(R.id.action_historyFragment_to_configFragment3)
                    true
                }

                // exploreMenu
                R.id.exploreTest -> {
                    true
                }

                R.id.exploreSettings -> {
                    goToSettingsFragment(R.id.action_exploreFragment_to_configFragment3)
                    true
                }

                else -> false
            }
        }
    }

    private fun showToolBarGroup(id: Int?) {
        if (id != null) {
            materialToolbar.menu.forEach { it.isVisible = it.groupId == id }
        }
        else {
            materialToolbar.menu.forEach { it.isVisible = false }
        }
    }
}