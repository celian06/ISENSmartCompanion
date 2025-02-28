package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import fr.isen.digiovanni.isensmartcompanion.models.Event



@Composable
fun EventDetailScreen(eventId: Int) {
    val event = getEventById(eventId)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = event.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Date: ${event.date}", fontSize = 18.sp)
        Text(text = "Location: ${event.location}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = event.description, fontSize = 16.sp)
    }
}

fun getEventById(eventId: Int): Event {
    // Fake data to return event by ID
    val eventList = listOf(
        Event(1, "BDE Evening", "A fun evening with games and music.", "2025-05-20", "ISEN Campus", "Social"),
        Event(2, "Gala", "A formal event with dinner and dancing.", "2025-06-15", "ISEN Campus", "Formal"),
        Event(3, "Cohesion Day", "A team-building day with outdoor activities.", "2025-07-10", "ISEN Campus", "Outdoor")
    )
    return eventList.first { it.id == eventId }
}
