package fr.isen.digiovanni.isensmartcompanion.ai

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiAIService(private val apiKey: String) {

    // Création de l'instance de GenerativeModel
    private val client: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash", // Nom du modèle
        apiKey = apiKey  // Ta clé API
    )

    // Fonction pour obtenir la réponse de Gemini AI
    suspend fun getAIResponse(question: String): String {
        return withContext(Dispatchers.IO) {
            try {
                // Collecte le flux de réponses
                val flowResponse = client.generateContentStream(question)
                // Collecte du flux et attente de la réponse
                var aiResponse = "No response from AI."

                flowResponse.collect { response ->
                    // Log chaque élément du flux pour voir ce qu'il contient
                    Log.d("GeminiAI", "Received part of the response: $response")
                    // Extraire le texte ou le contenu de la réponse
                    aiResponse = response.text ?: "No text in the response."
                }

                aiResponse  // Retourner la réponse finale
            } catch (e: Exception) {
                Log.e("GeminiAI", "Error: ${e.message}")
                "Error processing AI response."
            }
        }
    }
}
