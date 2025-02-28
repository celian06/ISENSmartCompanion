package fr.isen.digiovanni.isensmartcompanion.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import fr.isen.digiovanni.isensmartcompanion.models.FakeEventRepository
import fr.isen.digiovanni.isensmartcompanion.models.Event
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import fr.isen.digiovanni.isensmartcompanion.api.RetrofitInstance




@Composable
fun EventsScreen(navController: NavHostController) {
    val eventList = remember { mutableStateListOf<Event>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                // Appel suspend pour récupérer les événements depuis l'API
                val response = RetrofitInstance.api.getEvents()
                Log.d("API Response", response.toString())
                eventList.clear()
                eventList.addAll(response)
            } catch (e: Exception) {
                // Gérer les erreurs
                e.printStackTrace()
                Log.e("API Error", "Failed to fetch events: ${e.message}")
            }
        }
    }

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