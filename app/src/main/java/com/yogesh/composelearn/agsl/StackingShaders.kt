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
fun ColumnScope.StackingShaders(modifier: Modifier = Modifier, f: Float) {
    val glowMask = """
        uniform shader uBitmap;
        uniform float uThreshold;
        
        half4 main(vec2 coord) {
            half4 c = uBitmap.eval(coord);
            float brightness = dot(c.rgb, vec3(0.299, 0.587, 0.114));
            return brightness > uThreshold ? c : half4(0.0);
        }
    """.trimIndent()
    val shadderCode = """
uniform float uTime;
uniform vec2 resolution;
uniform shader uBitmap;
uniform vec2 uTouch;

uniform shader uGlowMask;

half4 main(vec2 fragCoord) {
    vec2 uv = fragCoord / resolution;
    float pixelSize = 1.0 / resolution.x;


    half4 col = half4(0.0);
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
        col += uBitmap.eval(fragCoord + vec2(i,j) * uTouch[0]/100.0);
    }
    }
    
//    col += uBitmap.eval()
    col /= 9;
    return col;
}

 """.trimIndent()

    val time = remember { mutableStateOf(0f) }
    val glowMaskShader = remember { RuntimeShader(glowMask) }
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
            glowMaskShader.apply {
                setInputShader("uBitmap", ImageShader(bitmap))
                setFloatUniform("uThreshold", 0.5f)
            }
            shader.apply {
                setFloatUniform("resolution", size.width, size.height)
                setFloatUniform("uTime", time.value)
                setInputShader("uBitmap", ImageShader(bitmap))
                setInputShader("uGlowMask", glowMaskShader)
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