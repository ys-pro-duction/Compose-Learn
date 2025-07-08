package com.yogesh.rotatingKnobe

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogesh.composelearn.R
import kotlin.math.PI
import kotlin.math.atan2

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RotatingVolumeKnobe(modifier: Modifier = Modifier) {
    Box (modifier = Modifier.background(Color.White).padding(56.dp).fillMaxSize(), contentAlignment = Alignment.Center){
        Box(modifier = Modifier.aspectRatio(2f/3f).shadow(60.dp,RoundedCornerShape(16.dp)).clip(RoundedCornerShape(20.dp)).background(Color.White), contentAlignment = Alignment.Center) {
            val volume = remember { mutableFloatStateOf(0f) }
            Knobe {
                volume.floatValue = it
//            Log.d("TAG", "RotatingVolumeKnobe: $it = ${it * ((365 - 25 - 25))}")
            }
            Text(
                "${(volume.floatValue * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            VolumeBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(top = 280.dp, start = 30.dp,end = 30.dp),
                volume = (volume.floatValue * 100).toInt()
            )
        }
    }
}

@Composable
fun Knobe(
    modifier: Modifier = Modifier, limitDegree: Float = 25f, onVolumeChance: (Float) -> Unit
) {
    val centerOffest = remember { mutableStateOf(Offset.Zero) }
    val volumeState = remember { mutableFloatStateOf(0f) }
    val isEnable = remember { mutableStateOf(true) }
    val animeDp = animateDpAsState(if (isEnable.value)  90.dp * volumeState.floatValue else 0.dp, tween())
    Image(
        painterResource(R.drawable.knobe), null, modifier = Modifier
            .onGloballyPositioned({
                centerOffest.value =
                    Offset(it.boundsInRoot().size.width / 2, it.boundsInRoot().size.height / 2)
//            Log.d("TAG", "Knobe: Bounds: $centerOffest")
            })
            .size(200.dp)
            .shadow(animeDp.value, CircleShape, spotColor = Color.Blue)
            .clip(CircleShape)
            .clickable {
                isEnable.value = !isEnable.value
                if (!isEnable.value) {
                    onVolumeChance(0f)
                    volumeState.floatValue = 0f
                }
                Log.d("TAG", "Knobe: ${isEnable.value}")
            }
            .aspectRatio(1f)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull()
                        change?.let {
                            if (
//                            event.type == PointerEventType.Press
//                            ||
                                event.type == PointerEventType.Move) {
                                val position = it.position
                                val _angel = atan2(
                                    centerOffest.value.x - position.x,
                                    centerOffest.value.y - position.y
                                ) * (180 / PI)
                                val angle = if (_angel < 0) -_angel else 365 - _angel
                                val finalVolume =
                                    (angle - (limitDegree)) / (365 - (2 * limitDegree))
                                if (angle < limitDegree || angle > 365 - limitDegree) return@let
                                if (!isEnable.value) isEnable.value = true
                                Log.d("TAG", "Knobe: $position,       ${centerOffest.value}")
//                            Log.d("TAG", "Knobe: $volume")
//                            Log.d(
//                                "TAG",
//                                "Knobe: ($angle - ( $limitDegree)) / (365 - (2 * $limitDegree)) = $volume"
//                            )
                                it.consume()
                                volumeState.floatValue = finalVolume.toFloat()
                                onVolumeChance(finalVolume.toFloat())
                            }
                        }
                    }
                }
            }
            .rotate(limitDegree + volumeState.floatValue * (365 - (2 * limitDegree))))
}

@Composable
fun VolumeBar(
    modifier: Modifier = Modifier,
    volume: Int,
    activeColor: Color = Color.Blue,
    inactiveColor: Color = Color.LightGray,
    barWidth: Dp = 10.dp,
    barHeight: Dp = 40.dp,
    barCount: Int = 10
) {
    Canvas(modifier = modifier) {
        Log.d("TAG", "VolumeBar: $volume")
        val perbarVolume = 100/barCount
        for (i in 0 until barCount) {
            drawLine(
                if (perbarVolume*(i+1) > volume) inactiveColor else activeColor,
                Offset((size.width/barCount/2)+(i*size.width/barCount), 0f),
                Offset((size.width/barCount/2)+(i*size.width/barCount), barHeight.toPx()),
                barWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}