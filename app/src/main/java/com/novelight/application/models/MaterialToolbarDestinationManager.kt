package com.novelight.application.models

import android.hardware.SensorPrivacyManager
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

    private val homeFragments: Set<Int> = setOf(
        R.id.libraryFragment,
        R.id.updatesFragment,
        R.id.historyFragment,
        R.id.exploreFragment
    )

    fun onFragmentChange(destination: NavDestination) {
        closeSearchViewIfExpanded()

        when (destination.id) {
            R.id.libraryFragment -> {
                fragmentLibraryMenuOptions()
            }

            R.id.updatesFragment -> {
                fragmentUpdatesMenuOptions()
            }

            R.id.historyFragment -> {
                fragmentHistorialMenuOptions()
            }

            R.id.exploreFragment -> {
                fragmentExplorarMenuOptions()
            }
        }

        materialToolbar.menu.findItem(R.id.settings).isVisible =
            homeFragments.contains(destination.id)
    }

    private fun goToSettingsFragment(actionId: Int) {
        materialToolbar.menu.forEach { it.isVisible = false }
        navController.navigate(actionId)
    }

    private fun closeSearchViewIfExpanded() {
        val searchMenuItem = materialToolbar.menu.findItem(R.id.librarySearch)
        if (searchMenuItem.isActionViewExpanded) {
            searchMenuItem.collapseActionView()
        }
    }

    private fun fragmentLibraryMenuOptions() {
        showToolBarGroup(R.id.libraryGroup)
        setupToolBarSearchView()

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.librarySearch -> {
                    setupToolBarSearchView()
                    true
                }

                R.id.settings -> {
                    goToSettingsFragment(R.id.action_libraryFragment_to_configFragment3)
                    true
                }

                else -> false
            }
        }
    }

    private fun fragmentUpdatesMenuOptions() {
        showToolBarGroup(R.id.updatesGroup)

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.updatesTest -> {
                    goToSettingsFragment(R.id.action_updatesFragment_to_configFragment3)
                    true
                }

                R.id.settings -> {
                    goToSettingsFragment(R.id.action_updatesFragment_to_configFragment3)
                    true
                }

                else -> false
            }
        }
    }

    private fun fragmentHistorialMenuOptions() {
        showToolBarGroup(R.id.historyGroup)

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.historyTest -> {
                    true
                }

                R.id.settings -> {
                    goToSettingsFragment(R.id.action_historyFragment_to_configFragment3)
                    true
                }

                else -> false
            }
        }
    }

    private fun fragmentExplorarMenuOptions() {
        showToolBarGroup(R.id.exploreGroup)

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.exploreTest -> {
                    true
                }

                R.id.settings -> {
                    goToSettingsFragment(R.id.action_exploreFragment_to_configFragment3)
                    true
                }

                else -> false
            }
        }
    }

    private fun setupToolBarSearchView() {
        val searchMenuItem = materialToolbar.menu.findItem(R.id.librarySearch)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(MyOnQueryTextListener(filterViewModel))
        searchView.isIconifiedByDefault = false

        val searchCloseButtonId =
            searchView.context.resources.getIdentifier("android:id/search_close_btn", null, null)
        val closeButton = searchView.findViewById<ImageView>(searchCloseButtonId)
        closeButton.setImageResource(R.drawable.blank)

        searchMenuItem.setOnActionExpandListener(MyOnActionExpandListener(searchView))
    }

    private fun showToolBarGroup(id: Int) {
        materialToolbar.menu.forEach { it.isVisible = it.groupId == id }
    }
}