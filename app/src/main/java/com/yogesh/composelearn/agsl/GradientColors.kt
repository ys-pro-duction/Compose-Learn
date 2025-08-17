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
fun RowScope.GradientColors(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shadderCode = """
uniform float time;      // Passed in seconds from Kotlin
uniform vec2 resolution;  // Canvas size

half4 main(vec2 fragCoord) {
    // Normalize coordinates (0–1)
    vec2 uv = fragCoord / resolution;
    
    half loopTime = abs((fract(time/10.0) * 10.0) -5.0 );
    // Make a moving wave pattern   (or just "time" for infinite loop)
    float wave = sin(uv.x * 2.0 + loopTime);

    // Map wave from [-1, 1] to [0, 1]
    float gradient = 0.5 + 0.5 * wave;

    // Create dynamic colors
    float red   = gradient;
    float green = 0.5 + 0.5 * sin(loopTime + uv.y * 3.0);
    float blue  = 0.5 + 0.5 * sin(loopTime * 1.5);

    return half4(red, green, blue, 1.0);
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
            shader.setFloatUniform("time",time.value)
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