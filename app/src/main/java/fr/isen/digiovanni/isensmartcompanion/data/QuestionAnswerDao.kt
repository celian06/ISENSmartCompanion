package fr.isen.digiovanni.isensmartcompanion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionAnswerDao {
    @Insert
    suspend fun insert(qa: QuestionAnswer)

    @Query("SELECT * FROM question_answers ORDER BY date DESC")
    suspend fun getAll(): List<QuestionAnswer>

    @Query("DELETE FROM question_answers WHERE id = :qaId")
    suspend fun delete(qaId: Int)

    @Query("DELETE FROM question_answers")
    suspend fun deleteAll()
}