package com.example.easenetics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "easenetics-db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            val lessons = listOf(
                Lesson(title = "Sending a Text Message", content = "1. Open Messages app.\n2. Tap 'New Message'.\n3. Type a number.\n4. Write and tap 'Send'.", category = "beginner"),
                Lesson(title = "Making a Phone Call", content = "1. Open Phone app.\n2. Dial a number.\n3. Tap 'Call'.", category = "beginner"),
                Lesson(title = "Browsing the Internet Safely", content = "1. Open a browser.\n2. Type a safe URL (e.g., www.google.com).\n3. Avoid suspicious links.", category = "intermediate"),
                Lesson(title = "Creating an Email", content = "1. Open Gmail.\n2. Tap 'Compose'.\n3. Enter recipient, subject, and message.\n4. Tap 'Send'.", category = "intermediate"),
                Lesson(title = "Setting Up Social Media", content = "1. Download an app (e.g., Facebook).\n2. Create an account.\n3. Set privacy settings.", category = "advanced"),
                Lesson(title = "Avoiding Online Scams", content = "1. Don’t share personal info.\n2. Verify website security (https).\n3. Ignore phishing emails.", category = "advanced")
            )
            lessons.forEach { db.lessonDao().insert(it) }
        }

        setContent {
            EaseneticsApp(db)
        }
    }
}

@Composable
fun EaseneticsApp(db: AppDatabase) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("lessons") { LessonsScreen(navController, db) }
        composable("quiz") { QuizScreen(navController, db) }
        composable("progress") { ProgressScreen(navController, db) }
        composable("videos") { VideoTutorialsScreen() }
        composable("chatbot") { ChatbotScreen() }
        composable("accessibility") { AccessibilitySettingsScreen() }
        composable("offline") { OfflineModeScreen() }
        composable("profile") { ProfileSettingsScreen() }
        composable("feedback") { FeedbackSupportScreen() }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Easenetics!",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Button(
                onClick = { navController.navigate("lessons") },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(width = 200.dp, height = 80.dp)
            ) {
                Text("Lessons", fontSize = 24.sp)
            }
            Button(
                onClick = { navController.navigate("quiz") },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(width = 200.dp, height = 80.dp)
            ) {
                Text("Quizzes", fontSize = 24.sp)
            }
            Button(
                onClick = { navController.navigate("progress") },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(width = 200.dp, height = 80.dp)
            ) {
                Text("Progress", fontSize = 24.sp)
            }
        }
    }
}
