package com.example.easenetics

import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoTutorialsScreen() {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/raw/sample_video")
            // Alternative: Use a URL for testing: MediaItem.fromUri("https://www.example.com/sample.mp4")
            setMediaItem(mediaItem)
            prepare()
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
                text = "Video Tutorials",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { exoPlayer.play() },
                    modifier = Modifier.size(width = 100.dp, height = 60.dp)
                ) {
                    Text("Play", fontSize = 20.sp)
                }
                Button(
                    onClick = { exoPlayer.pause() },
                    modifier = Modifier.size(width = 100.dp, height = 60.dp)
                ) {
                    Text("Pause", fontSize = 20.sp)
                }
                Button(
                    onClick = { exoPlayer.seekTo(0); exoPlayer.play() },
                    modifier = Modifier.size(width = 100.dp, height = 60.dp)
                ) {
                    Text("Repeat", fontSize = 20.sp)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}