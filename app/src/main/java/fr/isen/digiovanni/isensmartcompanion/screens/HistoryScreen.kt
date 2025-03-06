package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Typography
import androidx.compose.runtime.livedata.observeAsState
import fr.isen.digiovanni.isensmartcompanion.data.AIInteraction
import fr.isen.digiovanni.isensmartcompanion.data.AIInteractionDao


@Composable
fun HistoryScreen(homeScreenViewModel: HomeScreenViewModel, aiInteractionDao: AIInteractionDao) {
    // Observer les interactions depuis le DAO
    val interactions by aiInteractionDao.getAllInteractions().observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Interaction History", fontSize = 24.sp)

        // Afficher les interactions dans une LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(interactions) { interaction ->
                HistoryItem(interaction)
            }
        }
    }
}

@Composable
fun HistoryItem(interaction: AIInteraction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Question: ${interaction.question}", style = MaterialTheme.typography.bodyLarge)
            Text("Answer: ${interaction.answer}", style = MaterialTheme.typography.bodyMedium)
            Text("Date: ${interaction.timestamp}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
