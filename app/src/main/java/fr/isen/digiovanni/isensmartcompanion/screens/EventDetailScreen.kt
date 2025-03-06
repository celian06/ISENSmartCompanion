package fr.isen.digiovanni.isensmartcompanion.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.res.painterResource
import fr.isen.digiovanni.isensmartcompanion.R
import fr.isen.digiovanni.isensmartcompanion.api.RetrofitInstance
import fr.isen.digiovanni.isensmartcompanion.models.Event
import fr.isen.digiovanni.isensmartcompanion.notifications.ReminderReceiver.Companion.scheduleNotification // Importation de la fonction statique
import kotlinx.coroutines.launch

@Composable
fun EventDetailScreen(eventId: String, context: Context) {
    val event = remember { mutableStateOf<Event?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val sharedPreferences = remember { context.getSharedPreferences("EventPrefs", Context.MODE_PRIVATE) }
    val isNotified = remember { mutableStateOf(sharedPreferences.getBoolean("event_$eventId", false)) }

    // Charger les détails de l'événement lorsque l'ID change
    LaunchedEffect(eventId) {
        coroutineScope.launch {
            try {
                val eventDetails = RetrofitInstance.api.getEventDetails(eventId.toInt().toString())
                event.value = eventDetails
            } catch (e: Exception) {
                Log.e("EventDetailScreen", "Error fetching event details: ${e.message}")
            }
        }
    }

    // Affichage des détails de l'événement
    event.value?.let { currentEvent ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = currentEvent.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${currentEvent.date}", fontSize = 18.sp)
            Text(text = "Location: ${currentEvent.location}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = currentEvent.description, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Icône pour activer/désactiver la notification
            IconButton(onClick = {
                val newState = !isNotified.value
                isNotified.value = newState
                sharedPreferences.edit().putBoolean("event_$eventId", newState).apply()

                if (newState) {
                    scheduleNotification(context, eventId, currentEvent.title)
                }
            }) {
                Icon(
                    painter = if (isNotified.value) {
                        painterResource(id = R.drawable.ic_notifications_on) // Icône activée
                    } else {
                        painterResource(id = R.drawable.ic_notifications_off) // Icône désactivée
                    },
                    contentDescription = "Toggle Notification"
                )
            }
        }
    } ?: run {
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
