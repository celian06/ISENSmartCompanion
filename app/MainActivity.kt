package fr.isen.digiovanni.isensmartcompanion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.digiovanni.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AssistantScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }

    }
}

@Composable
fun AssistantScreen(modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var aiResponse by remember { mutableStateOf("Ask me anything!") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo), // Ajoute un logo dans res/drawable
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
            aiResponse = "Fake AI Response: ${userInput.text}" // Réponse fictive
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

@Preview(showBackground = true)
@Composable
fun AssistantScreenPreview() {
    ISENSmartCompanionTheme {
        AssistantScreen()
    }
}
