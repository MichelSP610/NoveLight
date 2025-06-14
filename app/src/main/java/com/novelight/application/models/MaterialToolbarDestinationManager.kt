package com.novelight.application.models

import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.novelight.application.R
import com.novelight.application.models.searchModels.MyOnActionExpandListener
import com.novelight.application.models.searchModels.MyOnQueryTextListener
import com.novelight.application.viewModels.FilterViewModel

class MaterialToolbarDestinationManager(
    private val materialToolbar: MaterialToolbar,
    private val navController: NavController,
    private val filterViewModel: FilterViewModel,
    private val bottomNav: BottomNavigationView
) {

    private val homeFragments: Set<Int> = setOf(
        R.id.libraryFragment,
        R.id.updatesFragment,
        R.id.exploreFragment
    )

    init {
        setToolBarMenuOptions()
    }

    fun onFragmentChange(destination: NavDestination) {
        closeSearchViewIfExpanded(R.id.librarySearch)
        closeSearchViewIfExpanded(R.id.exploreSearch)
        showToolBarGroup(destination.id)
        showBottomNavIfHomeFragment(destination.id)
    }

    private fun goToSettingsFragment(actionId: Int) {
        showToolBarGroup(null)
        navController.navigate(actionId)
    }

    private fun setupToolBarSearchView(searchViewId: Int) {
        val searchMenuItem = materialToolbar.menu.findItem(searchViewId)
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

    private fun showBottomNavIfHomeFragment(fragmentId: Int) {
        bottomNav.isVisible = homeFragments.contains(fragmentId)
    }

    private fun closeSearchViewIfExpanded(searchViewId: Int) {
        val searchMenuItem = materialToolbar.menu.findItem(searchViewId)
        if (searchMenuItem.isActionViewExpanded) {
            searchMenuItem.collapseActionView()
        }
    }

    private fun setToolBarMenuOptions() {
        setupToolBarSearchView(R.id.librarySearch)
        setupToolBarSearchView(R.id.exploreSearch)

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


                // exploreMenu
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