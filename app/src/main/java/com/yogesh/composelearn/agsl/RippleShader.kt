package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ShaderBrush
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Language("AGSL")
fun RowScope.RippleShader(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shaderCode = """
        uniform float2 resolution;
        uniform float time;

        half4 main(float2 fragCoord) {
            // Normalize coords
            float2 uv = fragCoord / resolution;
            uv = uv * 2.0 - 1.0;
            uv.x *= resolution.x / resolution.y;

            // Distance from center
            float dist = length(uv);

            // Moving wave pattern
            float wave = sin((dist * 2200.0) + time) * 0.5 + 0.5;

            // Colors
            half3 color1 = half3(1.0, 0.0, 0.0); // red
            half3 color2 = half3(0.0, 0.0, 1.0); // blue

            // Blend
            half3 finalColor = mix(color1, color2, wave);

            return half4(finalColor, 1.0);
        }

    """.trimIndent()
    val shader = remember() { RuntimeShader(shaderCode) }
    val time = remember { mutableStateOf(0f) }
    val animatedTime = animateFloatAsState(time.value, animationSpec = tween(1100, easing = LinearEasing))
    val weight = remember { mutableStateOf(f.floatValue) }

    Canvas(modifier = modifier
        .clickable(onClick = {
            if (weight.value <= 1f) {
                weight.value = Integer.MAX_VALUE.toFloat()
                f.floatValue = Integer.MAX_VALUE.toFloat()
            } else {
                weight.value = 1f
                f.floatValue = 1f
            }
        })
        .weight(weight.value)) {
        shader.setFloatUniform("resolution", size.width, size.height)
        shader.setFloatUniform("time", animatedTime.value)
        drawRect(ShaderBrush(shader))

    }
    val currentTime = remember { System.currentTimeMillis() }
    LaunchedEffect(weight.value > 2) {
        while (weight.value > 2){
            time.value = ((System.currentTimeMillis() - currentTime) / 35).toFloat()
            delay(1000)
        }
    }
}
