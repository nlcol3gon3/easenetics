package com.example.easenetics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResult)

    @Query("SELECT * FROM quiz_results")
    suspend fun getAllResults(): List<QuizResult>

    @Query("SELECT COUNT(*) FROM quiz_results WHERE score = 100")
    suspend fun getCompletedCount(): Int
}
//