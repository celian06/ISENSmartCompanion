package fr.isen.digiovanni.isensmartcompanion.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import fr.isen.digiovanni.isensmartcompanion.api.RetrofitInstance
import fr.isen.digiovanni.isensmartcompanion.models.Event
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment


@Composable
fun EventDetailScreen(eventId: String) {
    val event = remember { mutableStateOf<Event?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Charger les détails de l'événement lorsque l'ID change
    LaunchedEffect(eventId) {
        coroutineScope.launch {
            try {
                val eventDetails = RetrofitInstance.api.getEventDetails(eventId.toInt().toString()) // Appel à l'API
                event.value = eventDetails
            } catch (e: Exception) {
                Log.e("EventDetailScreen", "Error fetching event details: ${e.message}")
            }
        }
    }

    // Affichage des détails
    event.value?.let { currentEvent ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = currentEvent.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${currentEvent.date}", fontSize = 18.sp)
            Text(text = "Location: ${currentEvent.location}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = currentEvent.description, fontSize = 16.sp)
        }
    } ?: run {
        // Afficher un message de chargement ou d'erreur si l'événement n'est pas encore disponible
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Loading event details...", fontSize = 16.sp)
        }
    }
}
