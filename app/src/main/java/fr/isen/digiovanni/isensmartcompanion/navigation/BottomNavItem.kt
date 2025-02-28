package fr.isen.digiovanni.isensmartcompanion.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Events : BottomNavItem("events", "Events", Icons.Filled.List)
    object History : BottomNavItem("history", "History", Icons.Filled.DateRange)
}