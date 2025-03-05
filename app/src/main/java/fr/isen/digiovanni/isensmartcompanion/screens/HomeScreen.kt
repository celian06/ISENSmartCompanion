package fr.isen.digiovanni.isensmartcompanion.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.digiovanni.isensmartcompanion.R
import fr.isen.digiovanni.isensmartcompanion.ai.GeminiAIService
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var aiResponse by remember { mutableStateOf("Ask me anything!") }
    val context = LocalContext.current
    val aiService = remember { GeminiAIService("AIzaSyDWTgskfQGhjPylkJ5v2GS_1kM6Yo7YH2U") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo_isen),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Titre
        Text(
            text = "ISEN Smart Companion",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ de saisie utilisateur
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Ask a question") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour soumettre la question
        Button(onClick = {
            // Lors de l'envoi de la question, appeler le service AI
            coroutineScope.launch {
                aiResponse = aiService.getAIResponse(userInput)
            }
            Toast.makeText(context, "Question Submitted", Toast.LENGTH_SHORT).show()
        }) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texte affichant la réponse de l'IA
        Text(
            text = aiResponse,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
