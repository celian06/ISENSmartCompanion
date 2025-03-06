package fr.isen.digiovanni.isensmartcompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AIInteraction::class, QuestionAnswer::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // DAO pour AIInteraction
    abstract fun aiInteractionDao(): AIInteractionDao

    // DAO pour QuestionAnswer
    abstract fun questionAnswerDao(): QuestionAnswerDao // Ajout du DAO pour QuestionAnswer

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ai_interactions_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
