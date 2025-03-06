package fr.isen.digiovanni.isensmartcompanion.data

import androidx.room.*
import androidx.lifecycle.LiveData

@Dao
interface AIInteractionDao {
    @Query("SELECT * FROM ai_interactions ORDER BY timestamp DESC")
    fun getAllInteractions(): LiveData<List<AIInteraction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: AIInteraction)

    @Query("DELETE FROM ai_interactions WHERE id = :interactionId")
    suspend fun deleteInteraction(interactionId: Int)

    @Query("DELETE FROM ai_interactions")
    suspend fun deleteAllInteractions()
}
