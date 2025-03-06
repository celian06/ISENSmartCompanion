package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import fr.isen.digiovanni.isensmartcompanion.data.AIInteraction
import fr.isen.digiovanni.isensmartcompanion.data.AIInteractionDao

@Composable
fun HistoryScreen(homeScreenViewModel: HomeScreenViewModel, aiInteractionDao: AIInteractionDao) {
    val interactions by aiInteractionDao.getAllInteractions().observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Interaction History", fontSize = 24.sp)

        Button(onClick = { homeScreenViewModel.deleteAllInteractions() }) {
            Text("Delete All History")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(interactions) { interaction ->
                HistoryItem(interaction, onDelete = { homeScreenViewModel.deleteInteraction(interaction.id) })
            }
        }
    }
}

@Composable
fun HistoryItem(interaction: AIInteraction, onDelete: () -> Unit) {
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
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}
