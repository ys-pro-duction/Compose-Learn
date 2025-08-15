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
fun ColumnScope.InstStyleFilter(modifier: Modifier = Modifier, f: Float) {
    val shadderCode = """
uniform float uTime;
uniform vec2 resolution;
uniform shader uBitmap;
uniform vec2 uTouch;

half4 main(vec2 fragCoord) {
    vec2 uv = fragCoord / resolution;
    vec2 touchUv = uTouch / resolution;

    half4 img = uBitmap.eval(fragCoord);

    // --- 1. Contrast & brightness ---
    img.rgb = (img.rgb - 0.5) * 1.2 + 0.5; // contrast
    img.rgb += 0.05; // brightness

    // --- 2. Warm tone overlay ---
    half3 warmTone = half3(1.0, 0.9, 0.8);
    img.rgb = mix(img.rgb, warmTone, 0.1);

    // --- 3. Vignette ---
    vec2 centered = uv - 0.5;
    float dist = length(centered);
    float vignette = smoothstep(0.7, 0.3, dist);
    img.rgb *= vignette;

    // --- 4. Touch-based focus light ---
    float glow = smoothstep(0.2, 0.0, distance(uv, touchUv));
    img.rgb += glow * 0.3;

    return half4(clamp(img.rgb, 0.0, 1.0), 1.0);
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