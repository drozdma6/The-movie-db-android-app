package cz.cvut.fit.drozdma6.semestral

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import cz.cvut.fit.drozdma6.semestral.databinding.ActivityMainBinding
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val destinationIdsWithoutToolbar = setOf(
        R.id.movieDetailFragment
    )
    private val topLevelDestinationsIds = setOf(
        R.id.movieDetailFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navController,
            saveState = false
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isDestinationWithoutToolbar = destination.id in destinationIdsWithoutToolbar
            binding.netflixImg.isVisible = !isDestinationWithoutToolbar
            val isTopLevelDestination = destination.id in topLevelDestinationsIds
            binding.bottomNavigation.isVisible = !isTopLevelDestination
        }
    }
}