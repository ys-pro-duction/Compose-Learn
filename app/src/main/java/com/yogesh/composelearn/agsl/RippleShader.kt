package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Language("AGSL")
fun RippleShader(modifier: Modifier = Modifier) {
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
            float wave = sin(dist * 10.0 - time * 4.0) * 0.5 + 0.5;

            // Colors
            half3 color1 = half3(1.0, 0.0, 0.0); // red
            half3 color2 = half3(0.0, 0.0, 1.0); // blue

            // Blend
            half3 finalColor = mix(color1, color2, wave);

            return half4(finalColor, 1.0);
        }

    """.trimIndent()
    val shader = remember() { RuntimeShader(shaderCode) }
    val time = remember { mutableStateOf(System.currentTimeMillis() / 1f) }
    Canvas(modifier) {
        shader.setFloatUniform("resolution", size.width, size.height)
        shader.setFloatUniform("time", time.value)
        drawRect(ShaderBrush(shader))

    }
    LaunchedEffect(Unit) {
        while (true){
            delay(500)
            time.value = (System.currentTimeMillis() % 10).toFloat()
            Log.d("RippleShader", "${(System.currentTimeMillis() % 10000).toFloat()}, Time updated: ${time.value}")
        }
    }
}
