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
fun RowScope.DrawShapes(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shadderCode = """
uniform float2 resolution;
uniform float uTime;

half4 main(float2 fragCoord) {
    float2 uv = fragCoord / resolution;
    float2 centeredAspect = uv;
    centeredAspect.x *= resolution.x / resolution.y;

    
    // Animated circle
    float radius = 0.15 + 0.05 * sin(uTime);
    float circleDist = length(centeredAspect - 0.5);
    float circle = step(circleDist, radius);

    // Animated rectangle
    float centerX = 0.5 + 0.3 * sin(uTime * 0.5);
    float2 diff = abs(uv - float2(centerX, 0.3)) - float2(0.2, 0.1);
    float rect = step(max(diff.x, diff.y), 0.0);

    // Combine
    float shape = max(circle, rect);

    return half4(circle, rect, shape, 1.0);
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