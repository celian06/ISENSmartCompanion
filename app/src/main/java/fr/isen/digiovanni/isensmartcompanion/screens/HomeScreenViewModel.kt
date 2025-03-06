package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import fr.isen.digiovanni.isensmartcompanion.data.AIInteractionDao
import fr.isen.digiovanni.isensmartcompanion.data.AIInteraction
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val aiInteractionDao: AIInteractionDao) : ViewModel() {

    fun saveQuestionAnswer(question: String, answer: String) {
        viewModelScope.launch {
            val interaction = AIInteraction(question = question, answer = answer, timestamp = System.currentTimeMillis())
            aiInteractionDao.insertInteraction(interaction)
        }
    }

    fun getAllInteractions(): LiveData<List<AIInteraction>> {
        return aiInteractionDao.getAllInteractions()
    }

    fun deleteInteraction(interactionId: Int) {
        viewModelScope.launch {
            aiInteractionDao.deleteInteraction(interactionId)
        }
    }

    fun deleteAllInteractions() {
        viewModelScope.launch {
            aiInteractionDao.deleteAllInteractions()
        }
    }
}
