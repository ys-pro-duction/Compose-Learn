package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RowScope.WavePattern(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shadderCode = """
uniform float uTime;
uniform vec2 resolution;

half4 main(vec2 fragCoord) {
    // Normalize coords
    vec2 uv = fragCoord / resolution;

    // Wave along X axis
    float wave = sin(uv.x * 20.0 + uTime * 2.0)+sin(uv.y*30+uTime);

    // Map -1..1 to 0..1
    float brightness = step(wave * 0.5 + 0.5,abs(sin(uTime/2)));

    return half4(wave, wave * 0.5 + 0.5, brightness, 1.0);
}



    """.trimIndent()

    val time = remember { mutableStateOf(0f) }
    val shader = remember { RuntimeShader(shadderCode) }
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
        drawIntoCanvas {
            shader.setFloatUniform("resolution",size.width,size.height)
            shader.setFloatUniform("uTime",time.value)
            drawRect(ShaderBrush(shader))
            it.drawRect(size.toRect(), Paint().apply { this.asFrameworkPaint().shader = shader })
        }
    }
    LaunchedEffect(weight.value > 2) {
        while (weight.value > 2){
            time.value += 0.05f
            delay(20)
        }
    }
}