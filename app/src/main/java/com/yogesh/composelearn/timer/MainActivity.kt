package com.yogesh.composelearn.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogesh.composelearn.timer.ui.theme.ComposeLearnTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {

                        var start = 5
                        var running by remember { mutableStateOf(false) }
                        var time by remember { mutableIntStateOf(start) }
                        val animatedTime =
                            animateFloatAsState(time.toFloat(),
                                tween(if (time == start) 0 else 1000, easing = LinearEasing))

                        CircularProgress(
                            modifier = Modifier.size(200.dp),
                            currentTimeMili = animatedTime.value,
                            maxTimeMili = start,
                            progressBarWidth = 16f,
                            progressBarPathWidth = 30f,
                            progressDotWidth = 36f,
                            progressBarPathColor = Color(0x4d808080)
                        )
                        Text("$time", fontWeight = FontWeight.Bold, fontSize = 48.sp)
                        Button(
                            {
                                if (time <= 0) time = start
                                running = !running
                            },
                            modifier = Modifier.padding(top = 220.dp),
                            colors = ButtonDefaults.buttonColors(if (running) Color.Red else Color.Green),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (running) "Stop" else "Start")
                        }
                        LaunchedEffect(running) {
                            while (running && time > 0) {
                                delay(1000)
                                time--
                                if (time == 0) running = false
                            }
                        }
                    }
                }
            }
        }
    }
}
