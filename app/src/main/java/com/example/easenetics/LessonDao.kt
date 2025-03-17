package com.example.easenetics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LessonDao {
    @Insert
    suspend fun insert(lesson: Lesson)

    @Query("SELECT * FROM lessons")
    suspend fun getAllLessons(): List<Lesson>
}
//