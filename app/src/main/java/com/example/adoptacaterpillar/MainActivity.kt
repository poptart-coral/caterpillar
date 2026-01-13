package com.example.adoptacaterpillar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adoptacaterpillar.ui.navigation.Screen
import com.example.adoptacaterpillar.ui.screens.AboutScreen
import com.example.adoptacaterpillar.ui.screens.BreedDetailScreen
import com.example.adoptacaterpillar.ui.screens.CatFactScreen
import com.example.adoptacaterpillar.ui.screens.RandomCatScreen
import com.example.adoptacaterpillar.ui.theme.AdoptACaterpillarTheme
import com.example.adoptacaterpillar.ui.viewmodel.CatViewModel
import com.example.adoptacaterpillar.ui.screens.BreedListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdoptACaterpillarTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: CatViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val items = listOf(
        Triple(Screen.RandomCat, "Random", Icons.Filled.Refresh),
        Triple(Screen.Breeds, "Breeds", Icons.Filled.Info),
        Triple(Screen.CatFacts, "Facts", Icons.Filled.Info),
        Triple(Screen.About, "About", Icons.Filled.Info)
    )

    // Show the back button if we are not on a root destination
    val canNavigateBack = navController.previousBackStackEntry != null
    val isRootDestination = items.any { it.first.route == currentRoute }
    val showBackButton = canNavigateBack && !isRootDestination

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when {
                            currentRoute == Screen.About.route -> "À propos"
                            else -> "Chat Aléatoire"
                        }
                    )
                },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { (screen, label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.RandomCat.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.RandomCat.route) { RandomCatScreen(viewModel) }

            composable(Screen.About.route) { AboutScreen() }
            composable(Screen.CatFacts.route) { CatFactScreen() }
            composable(Screen.Breeds.route) {
                BreedListScreen { breedId ->
                    navController.navigate(Screen.BreedDetail.createRoute(breedId))
                }
            }
            composable(Screen.BreedDetail.route) { backStackEntry ->
                val breedId = backStackEntry.arguments?.getString("breedId")
                BreedDetailScreen(breedId)
            }
        }
    }
}
