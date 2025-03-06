package fr.isen.digiovanni.isensmartcompanion.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.isen.digiovanni.isensmartcompanion.data.AIInteractionDao

class HomeScreenViewModelFactory(private val aiInteractionDao: AIInteractionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeScreenViewModel(aiInteractionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
