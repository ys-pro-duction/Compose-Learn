package com.yogesh.composelearn.animated_progress_bar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun CircularAnimatedProgressBar(modifier: Modifier = Modifier) {
    var progress by remember { mutableStateOf(0) }
    val progressAnim = animateIntAsState(
        progress, animationSpec = tween(500, easing = LinearEasing)
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgress(modifier, progress = progressAnim.value)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button({
            progress += 10
            if (progress > 100) progress = 0
        }, modifier = Modifier.padding(bottom = 36.dp)) {
            Text("+")
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(400)
            progress += 10
            if (progress > 100) progress = 0
        }
    }


}

@Composable
fun BoxScope.CircularProgress(
    modifier: Modifier = Modifier,
    progress: Int,
    progressColor: Color = Color.Blue,
    progressWidth: Dp = 18.dp,
    size: Dp = 120.dp,
    maxProgress: Int = 100
) {
    Canvas(
        modifier = modifier
            .size(size+(progress.dp/2))
            .padding(progressWidth / 2)
    ) {
        drawArc(
            Brush.radialGradient(
                listOf(Color.Cyan, Color.Red, Color.Yellow, Color.Cyan, Color.Red, Color.Yellow, Color.Cyan, Color.Red, Color.Yellow, Color.Cyan, Color.Red, Color.Yellow, Color.Cyan),
                radius = progress.toFloat()*5 + 200
            ),
            -90f,
            360f * (progress.toFloat() / maxProgress),
            false,
            style = Stroke(
                progressWidth.toPx()+progress,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
    Text("$progress%", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = modifier)
}