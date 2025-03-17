package com.example.easenetics

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Lesson::class, QuizResult::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE lessons ADD COLUMN category TEXT NOT NULL DEFAULT 'beginner'")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE quiz_results (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        lessonTitle TEXT NOT NULL,
                        score INTEGER NOT NULL,
                        timestamp INTEGER NOT NULL
                    )
                """.trimIndent())
            }
        }
    }
}
