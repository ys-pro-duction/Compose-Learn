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
fun RowScope.AnimatedShape(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shadderCode = """
uniform float uTime;
uniform vec2 resolution;

half4 main(vec2 fragCoord) {
    // Normalize
    vec2 uv = fragCoord / resolution - 0.5;
    uv.x *= resolution.x / resolution.y;

    // Moving rectangle distance
    float angle = uTime; // rotation speed
mat2 rot = mat2(cos(angle), -sin(angle),
                sin(angle),  cos(angle));
vec2 rotatedUV = rot * uv;

vec2 rectSize = vec2(0.3, 0.1);
vec2 d = abs(rotatedUV) - rectSize;
float rectDist = length(max(d, 0.0));


    // Circle distance
    float circleDist = length(uv)*abs(sin(uTime)*2) - 0.10;

    // Combine: intersaction of circle and rectangle
    float shape = max(circleDist, rectDist);
    // Combine: union of circle and rectangle
    float shape2 = min(circleDist, rectDist);
    // Combine: minus of circle and rectangle
    float shape3 = max(-circleDist, rectDist);

    // Color
    float inside = smoothstep(0.01, 0.0, shape3);
    half3 col = mix(half3(0.1, 0.2, 0.8), half3(0.8, 0.2, 0.1), inside);

    return half4(col, 1.0);
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
            delay(17)
        }
    }
}