package com.novelight.application

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.novelight.application.databinding.ActivityMainBinding
import com.novelight.application.models.MyOnQueryTextListener


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val homeFragments: Set<Int> = setOf(
        R.id.libraryFragment,
        R.id.updatesFragment,
        R.id.historyFragment,
        R.id.exploreFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfig = AppBarConfiguration(navController.graph)

        val toolbarAppBarConfig = AppBarConfiguration(homeFragments)

        findViewById<Toolbar>(R.id.mainToolbar)
            .setupWithNavController(navController, toolbarAppBarConfig)

        val bottomNavView = binding.bottomNav
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            onFragmentChange(destination)
        }
    }

    private fun goToSettingsFragment(actionId: Int) {
        binding.mainToolbar.menu.forEach { it.isVisible = false }
        navController.navigate(actionId)
    }

    private fun onFragmentChange(destination: NavDestination) {
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

        binding.mainToolbar.menu.findItem(R.id.settings).isVisible = homeFragments.contains(destination.id)
    }

    private fun fragmentLibraryMenuOptions() {
        showToolBarGroup(R.id.libraryGroup)

        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.librarySearch -> {
                    val searchMenuItem = binding.mainToolbar.menu.findItem(R.id.librarySearch)
                    val searchView = searchMenuItem.actionView as SearchView
                    searchMenuItem.expandActionView()
                    searchView.setOnQueryTextListener(MyOnQueryTextListener())
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

        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
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

        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.historyTest ->  {
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

        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
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

    private fun showToolBarGroup(id: Int) {
        binding.mainToolbar.menu.forEach { it.isVisible = it.groupId == id}
    }

    override fun onSupportNavigateUp(): Boolean {
        // replace navigation up button with nav drawer button when on start destination
        return NavigationUI.navigateUp(navController, appBarConfig)
    }
}