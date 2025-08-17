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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.imageResource
import com.yogesh.composelearn.R
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
// TODO: NOT WORKING
fun RowScope.BlurSharpen(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shadderCode = """
    uniform float uTime;
    uniform vec2 resolution;
    uniform shader uBitmap;
    

    half4 main(vec2 fragCoord) {
        vec2 uv = fragCoord / resolution;
        vec2 pixel = 1.0 / resolution; // Size of 1 pixel in UV space
    
        half4 col = half4(0.0);
    
        // 3x3 blur kernel
//        for (int x = -1; x <= 1; x++) {
//            for (int y = -1; y <= 1; y++) {
//                vec2 offset = vec2(x, y) * pixel;
//                col += uBitmap.eval(uv + offset);
//            }
//        }
//        col /= 9.0; // Average the 9 samples

        
        col += uBitmap.eval(uv + vec2(-1, -1) * pixel) * 0.0;
        col += uBitmap.eval(uv + vec2(-1, 0) * pixel) * -1.0;
        col += uBitmap.eval(uv + vec2(-1, 1) * pixel) * 0.0;
        col += uBitmap.eval(uv + vec2(0, -1) * pixel) * -1.0;
        col += uBitmap.eval(uv + vec2(0, 0) * pixel) * 5.0;
        col += uBitmap.eval(uv + vec2(0, 1) * pixel) * -1.0;
        col += uBitmap.eval(uv + vec2(1, -1) * pixel) * 0.0;
        col += uBitmap.eval(uv + vec2(1, 0) * pixel) * -1.0;
        col += uBitmap.eval(uv + vec2(1, 1) * pixel) * 0.0;
        
    
    
        return col;
    }

    """.trimIndent()

    val time = remember { mutableStateOf(0f) }
    val shader = remember { RuntimeShader(shadderCode) }
    val bitmap = ImageBitmap.imageResource(R.drawable.pthree)
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
            .weight(weight.value)
    ) {
        drawIntoCanvas {
            shader.setFloatUniform("resolution", size.width, size.height)
            shader.setFloatUniform("uTime", time.value)
            shader.setInputShader("uBitmap", ImageShader(bitmap))
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