package fr.isen.digiovanni.isensmartcompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_interactions")
data class AIInteraction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Clé primaire auto-générée
    val question: String,  // Question posée à l'IA
    val answer: String,    // Réponse de l'IA
    val timestamp: Long = System.currentTimeMillis() // Date et heure de l'échange
)