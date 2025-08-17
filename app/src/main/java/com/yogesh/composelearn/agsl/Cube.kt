package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import com.yogesh.composelearn.R
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ColumnScope.Cube(modifier: Modifier = Modifier, f: Float) {
    val shadderCode = """
uniform float uTime;
uniform vec2 resolution;
uniform shader uBitmap;
uniform vec2 uTouch;


mat3 rotateX(float a) {
    float c = cos(a), s = sin(a);
    return mat3(1.0, 0.0, 0.0,
                0.0, c, -s,
                0.0, s, c);
}

mat3 rotateY(float a) {
    float c = cos(a), s = sin(a);
    return mat3(c, 0.0, s,
                0.0, 1.0, 0.0,
                -s, 0.0, c);
}

half4 main(vec2 fragCoord) {
    // Normalize coordinates to [-1, 1]
    vec2 uv = (fragCoord - 0.5 * resolution) / resolution.y;

    // Rotation
    float angle = uTouch[0]/100.0;
    mat3 rot = rotateY(angle) * rotateX(angle * 0.7);

    float cubeSize = uTouch[1]/1000.0;

    // Define cube vertices manually (8 corners)
    vec3 v0 = rot * vec3(-1.0, -1.0, -1.0) * cubeSize;
    vec3 v1 = rot * vec3( 1.0, -1.0, -1.0) * cubeSize;
    vec3 v2 = rot * vec3( 1.0,  1.0, -1.0) * cubeSize;
    vec3 v3 = rot * vec3(-1.0,  1.0, -1.0) * cubeSize;
    vec3 v4 = rot * vec3(-1.0, -1.0,  1.0) * cubeSize;
    vec3 v5 = rot * vec3( 1.0, -1.0,  1.0) * cubeSize;
    vec3 v6 = rot * vec3( 1.0,  1.0,  1.0) * cubeSize;
    vec3 v7 = rot * vec3(-1.0,  1.0,  1.0) * cubeSize;

    // Project to 2D
    vec2 p0 = v0.xy / (v0.z + 3.0);
    vec2 p1 = v1.xy / (v1.z + 3.0);
    vec2 p2 = v2.xy / (v2.z + 3.0);
    vec2 p3 = v3.xy / (v3.z + 3.0);
    vec2 p4 = v4.xy / (v4.z + 3.0);
    vec2 p5 = v5.xy / (v5.z + 3.0);
    vec2 p6 = v6.xy / (v6.z + 3.0);
    vec2 p7 = v7.xy / (v7.z + 3.0);

    // Find distance to cube edges
    float d = 1e5;

    // bottom square
    d = min(d, distance(uv, mix(p0, p1, clamp(dot(uv - p0, normalize(p1 - p0)) / length(p1 - p0), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p1, p2, clamp(dot(uv - p1, normalize(p2 - p1)) / length(p2 - p1), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p2, p3, clamp(dot(uv - p2, normalize(p3 - p2)) / length(p3 - p2), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p3, p0, clamp(dot(uv - p3, normalize(p0 - p3)) / length(p0 - p3), 0.0, 1.0))));

    // top square
    d = min(d, distance(uv, mix(p4, p5, clamp(dot(uv - p4, normalize(p5 - p4)) / length(p5 - p4), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p5, p6, clamp(dot(uv - p5, normalize(p6 - p5)) / length(p6 - p5), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p6, p7, clamp(dot(uv - p6, normalize(p7 - p6)) / length(p7 - p6), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p7, p4, clamp(dot(uv - p7, normalize(p4 - p7)) / length(p4 - p7), 0.0, 1.0))));

    // verticals
    d = min(d, distance(uv, mix(p0, p4, clamp(dot(uv - p0, normalize(p4 - p0)) / length(p4 - p0), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p1, p5, clamp(dot(uv - p1, normalize(p5 - p1)) / length(p5 - p1), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p2, p6, clamp(dot(uv - p2, normalize(p6 - p2)) / length(p6 - p2), 0.0, 1.0))));
    d = min(d, distance(uv, mix(p3, p7, clamp(dot(uv - p3, normalize(p7 - p3)) / length(p7 - p3), 0.0, 1.0))));

    // Draw lines
    float edgeThickness = 0.01;
    float line = smoothstep(edgeThickness, edgeThickness * 0.5, d);

    vec3 color = mix(vec3(1.0, 0.8, 0.2), vec3(0.0), line);

    return vec4(color, 1.0);
}

 """.trimIndent()

    val time = remember { mutableStateOf(0f) }
    val shader = remember { RuntimeShader(shadderCode) }
    val weight = remember { mutableStateOf(f) }
    val bitmap = ImageBitmap.imageResource(R.drawable.pthree)

    var touch by remember { mutableStateOf(Offset.Zero) }
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                this.detectDragGestures { p, offset0 ->
                    val offset = p.position
                    touch = offset
//                    touch = Offset(touch.x+offset0.x,touch.y+offset0.y)
                    if (offset.x < 200 && offset.y < 200) {
                        weight.value = Integer.MAX_VALUE.toFloat()
                    }else if (offset.x > size.width-200 && offset.y < 200){
                        weight.value = f
                    }
                }
            }
            .weight(weight.value)
    ) {
        drawIntoCanvas {
            shader.apply {
                setFloatUniform("resolution", size.width, size.height)
                setFloatUniform("uTime", time.value)
                setInputShader("uBitmap", ImageShader(bitmap))
                setFloatUniform("uTouch", floatArrayOf(touch.x, touch.y))
            }
            drawRect(ShaderBrush(shader))
            it.drawRect(size.toRect(), Paint().apply { this.asFrameworkPaint().shader = shader })
        }
    }
    LaunchedEffect(weight.value > 2) {
        while (weight.value > 2) {
            time.value += 0.05f
            delay(17)
        }
    }
}