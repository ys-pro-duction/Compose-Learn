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
fun RowScope.OverlayPatternsImages(modifier: Modifier = Modifier, f: MutableFloatState) {
    val shadderCode = """
    uniform float uTime;
    uniform vec2 resolution;
    uniform shader uBitmap;
    

    half4 main(vec2 fragCoord) {
        vec2 uv = fragCoord / resolution -0.5;
        vec2 pixel = 1.0 / fragCoord; // Size of 1 pixel in UV space
        
        float hori = step(fract(uv.y*resolution.y/resolution.x * 10.0+uTime+uv.x + uv.y), 0.2 );
        float ver = step(fract(uv.x * 10.0+uTime+uv.x + uv.y),0.2); // uv.x + uv.y for diagonal
        uv.x *= resolution.x/resolution.y;
        float dist = length(uv)*2;
        half4 vintage = half4(0.0,0.0,0.0,dist);
//        return uBitmap.eval(fragCoord)+atan(uv.x,uv.y);
        half4 color = uBitmap.eval(fragCoord);
        color = mix(color, half4(1.0),max(hori,ver)*0.4);
        color = mix(color, vintage,dist);
        return color;
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