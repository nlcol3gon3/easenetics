package com.example.easenetics

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun LessonsScreen(navController: NavController, db: AppDatabase) {
    val context = LocalContext.current
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }
    var isTtsReady by remember { mutableStateOf(false) }
    val lessons = remember { mutableStateListOf<Lesson>() }
    val scope = rememberCoroutineScope()

    // Initialize TTS and load lessons
    LaunchedEffect(Unit) {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                isTtsReady = true
            }
        }
        scope.launch {
            lessons.clear()
            lessons.addAll(db.lessonDao().getAllLessons())
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lessons",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                val categories = listOf("beginner", "intermediate", "advanced")
                categories.forEach { category ->
                    item {
                        Text(
                            text = category.replaceFirstChar { it.uppercase() },
                            fontSize = 26.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    val categoryLessons = lessons.filter { it.category == category }
                    items(categoryLessons) { lesson ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = lesson.title,
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = lesson.content,
                                    fontSize = 20.sp
                                )
                                Button(
                                    onClick = {
                                        if (isTtsReady) {
                                            textToSpeech?.speak(
                                                "${lesson.title}. ${lesson.content}",
                                                TextToSpeech.QUEUE_FLUSH,
                                                null,
                                                null
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .size(width = 200.dp, height = 60.dp),
                                    enabled = isTtsReady
                                ) {
                                    Text("Read Aloud", fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { navController.navigate("quiz") },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(width = 200.dp, height = 80.dp)
            ) {
                Text("Take Quiz", fontSize = 24.sp)
            }
        }
    }
}
//