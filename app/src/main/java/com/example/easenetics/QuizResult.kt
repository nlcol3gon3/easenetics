package com.example.easenetics

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lessonTitle: String, // Links to the lesson
    val score: Int,          // e.g., 100 for correct, 0 for incorrect
    val timestamp: Long = System.currentTimeMillis() // When the quiz was taken
)
