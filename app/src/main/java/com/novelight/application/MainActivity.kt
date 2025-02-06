package com.novelight.application

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.novelight.application.databinding.ActivityMainBinding
import com.novelight.application.models.MaterialToolbarDestinationManager
import com.novelight.application.viewModels.FilterViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var materialToolbarDestinationManager: MaterialToolbarDestinationManager
    private val filterViewModel: FilterViewModel by viewModels<FilterViewModel>()
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

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfig = AppBarConfiguration(navController.graph)

        val toolbarAppBarConfig = AppBarConfiguration(homeFragments)

        findViewById<Toolbar>(R.id.mainToolbar)
            .setupWithNavController(navController, toolbarAppBarConfig)

        val bottomNavView = binding.bottomNav
        bottomNavView.setupWithNavController(navController)

        materialToolbarDestinationManager =
            MaterialToolbarDestinationManager(binding.mainToolbar, navController, filterViewModel)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            materialToolbarDestinationManager.onFragmentChange(destination)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // replace navigation up button with nav drawer button when on start destination
        return NavigationUI.navigateUp(navController, appBarConfig)
    }
}