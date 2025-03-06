package fr.isen.digiovanni.isensmartcompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_answers")
data class QuestionAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String,
    val date: Long = System.currentTimeMillis()
)