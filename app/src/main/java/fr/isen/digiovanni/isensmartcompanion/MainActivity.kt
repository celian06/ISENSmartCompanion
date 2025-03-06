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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                val navController = rememberNavController()

                // Créer ou obtenir le DAO pour les opérations sur la base de données
                val aiInteractionDao = AppDatabase.getDatabase(applicationContext).aiInteractionDao()

                // Créer le ViewModel en utilisant le DAO approprié
                val homeScreenViewModel: HomeScreenViewModel = viewModel(
                    factory = HomeScreenViewModelFactory(aiInteractionDao) // Passer AIInteractionDao ici
                )

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavigationGraph(
                        navController,
                        Modifier.padding(innerPadding),
                        homeScreenViewModel, // Passer le ViewModel
                        aiInteractionDao // Passer AIInteractionDao pour l'historique
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
    homeScreenViewModel: HomeScreenViewModel, // Paramètre pour le ViewModel
    aiInteractionDao: AIInteractionDao // Paramètre pour passer AIInteractionDao
) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(homeScreenViewModel = homeScreenViewModel) // Passer le ViewModel
        }
        composable(BottomNavItem.Events.route) { EventsScreen(navController) }
        composable("eventDetail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: "0"
            EventDetailScreen(eventId)
        }
        composable(BottomNavItem.History.route) {
            HistoryScreen(homeScreenViewModel = homeScreenViewModel, aiInteractionDao = aiInteractionDao) // Passer AIInteractionDao à HistoryScreen
        }
    }
}
