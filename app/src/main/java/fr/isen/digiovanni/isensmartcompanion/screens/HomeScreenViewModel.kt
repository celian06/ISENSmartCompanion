package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.isen.digiovanni.isensmartcompanion.data.AIInteractionDao
import fr.isen.digiovanni.isensmartcompanion.data.AIInteraction
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val aiInteractionDao: AIInteractionDao) : ViewModel() {
    // Fonction pour ajouter une question/réponse dans la base de données
    fun saveQuestionAnswer(question: String, answer: String) {
        viewModelScope.launch {
            val interaction = AIInteraction(question = question, answer = answer, timestamp = System.currentTimeMillis())
            aiInteractionDao.insertInteraction(interaction)
        }
    }

    // Fonction pour récupérer toutes les interactions
    fun getAllInteractions(): LiveData<List<AIInteraction>> {
        return aiInteractionDao.getAllInteractions()
    }
}
