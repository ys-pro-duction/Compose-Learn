package com.yogesh.composelearn.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun CircularTimer(modifier: Modifier = Modifier) {
    CircularProgress()
}

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier.size(100.dp).padding(5.dp),
    progressBarColor: Color = Color(0xff10e700),
    progressDotColor: Color = Color.Green,
    progressBarPathColor: Color = Color.LightGray,
    currentTimeMili: Float = 10f,
    maxTimeMili: Int = 100,
    progressBarWidth: Float = 5f,
    progressDotWidth: Float = 10f,
    progressBarPathWidth: Float = 10f
) {
    Canvas(modifier = modifier) {
        drawArc(
            progressBarPathColor,
            135f,
            270f,
            false,
            style = Stroke(progressBarPathWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        drawArc(
            progressBarColor,
            135f,
            (270/maxTimeMili.toFloat())*currentTimeMili,
            false,
            style = Stroke(progressBarWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        drawArc(
            progressDotColor,
            45f-(270/maxTimeMili.toFloat())*(maxTimeMili-currentTimeMili),
            -0.02f,
            false,
            style = Stroke(progressDotWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

    }
}