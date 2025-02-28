package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.digiovanni.isensmartcompanion.models.FakeEventRepository
import fr.isen.digiovanni.isensmartcompanion.models.Event
import androidx.navigation.NavHostController


@Composable
fun EventsScreen(navController: NavHostController) {
    val eventList = listOf(
        Event(1, "BDE Evening", "A fun evening with games and music.", "2025-05-20", "ISEN Campus", "Social"),
        Event(2, "Gala", "A formal event with dinner and dancing.", "2025-06-15", "ISEN Campus", "Formal"),
        Event(3, "Cohesion Day", "A team-building day with outdoor activities.", "2025-07-10", "ISEN Campus", "Outdoor")
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Events List", fontSize = 24.sp)
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(eventList) { event ->
                EventItem(event, navController)
            }
        }
    }
}

@Composable
fun EventItem(event: Event, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("eventDetail/${event.id}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, fontSize = 20.sp)
            Text(text = event.date, fontSize = 14.sp)
            Text(text = event.location, fontSize = 14.sp)
        }
    }
}