package fr.isen.digiovanni.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import fr.isen.digiovanni.isensmartcompanion.screens.*
import fr.isen.digiovanni.isensmartcompanion.navigation.BottomNavItem
import fr.isen.digiovanni.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import fr.isen.digiovanni.isensmartcompanion.data.AppDatabase
import fr.isen.digiovanni.isensmartcompanion.data.AIInteractionDao
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                val navController = rememberNavController()

                val aiInteractionDao = AppDatabase.getDatabase(applicationContext).aiInteractionDao()

                val homeScreenViewModel: HomeScreenViewModel = viewModel(
                    factory = HomeScreenViewModelFactory(aiInteractionDao)
                )

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavigationGraph(
                        navController,
                        Modifier.padding(innerPadding),
                        homeScreenViewModel,
                        aiInteractionDao
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Events, BottomNavItem.History)

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = false,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel,
    aiInteractionDao: AIInteractionDao
) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(homeScreenViewModel = homeScreenViewModel)
        }
        composable(BottomNavItem.Events.route) { EventsScreen(navController) }
        composable("eventDetail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: "0"
            val context = LocalContext.current // Récupérer le contexte local
            EventDetailScreen(eventId, context) // Passer le contexte à EventDetailScreen
        }
        composable(BottomNavItem.History.route) {
            HistoryScreen(homeScreenViewModel = homeScreenViewModel, aiInteractionDao = aiInteractionDao)
        }
    }
}
