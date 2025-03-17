package com.example.easenetics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(navController: NavController, db: AppDatabase) { // Added db parameter
    var feedback by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What do you tap to send a text message?",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Button(
                onClick = {
                    feedback = "Correct! Tap 'Send' to send your message."
                    scope.launch {
                        db.quizResultDao().insert(
                            QuizResult(lessonTitle = "Sending a Text Message", score = 100)
                        )
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(width = 200.dp, height = 60.dp)
            ) {
                Text("Send", fontSize = 20.sp)
            }
            Button(
                onClick = {
                    feedback = "Incorrect. 'Cancel' stops the message. Try again."
                    scope.launch {
                        db.quizResultDao().insert(
                            QuizResult(lessonTitle = "Sending a Text Message", score = 0)
                        )
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(width = 200.dp, height = 60.dp)
            ) {
                Text("Cancel", fontSize = 20.sp)
            }
            Button(
                onClick = {
                    feedback = "Incorrect. 'Back' exits the app. Try again."
                    scope.launch {
                        db.quizResultDao().insert(
                            QuizResult(lessonTitle = "Sending a Text Message", score = 0)
                        )
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(width = 200.dp, height = 60.dp)
            ) {
                Text("Back", fontSize = 20.sp)
            }
            Text(
                text = feedback,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .weight(1f)
            )
            Button(
                onClick = { navController.navigate("progress") },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(width = 200.dp, height = 80.dp)
            ) {
                Text("View Progress", fontSize = 24.sp)
            }
        }
    }
}
//