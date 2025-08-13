package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun ColumnScope.BitmapInShader(modifier: Modifier = Modifier, f: Float) {
    val shadderCode = """
    uniform float uTime;
    uniform vec2 resolution;
    uniform shader uBitmap;
    
    half4 main(vec2 fragCoord) {
        half4 c = uBitmap.eval(fragCoord);
    
        // invert
        c.rgb = sin(uTime/2) - c.rgb;

//        // grayscale
//        float gray = dot(c.rgb, vec3(0.299, 0.587, 0.114));
//        c.rgb = vec3(gray);

        // brightness
        c.rgb *= sin(uTime/2);

        // contrast
        half contrast = 1+abs(sin(uTime/2));
        c.rgb =  (c.rgb - 0.5) * contrast + 0.5;
        
        return c;
    }
    """.trimIndent()

    val time = remember { mutableStateOf(0f) }
    val shader = remember { RuntimeShader(shadderCode) }
    val weight = remember { mutableStateOf(f) }
    val bitmap = ImageBitmap.imageResource(R.drawable.pthree)
    Canvas(
        modifier = modifier
            .clickable(onClick = {
                if (weight.value <= 1f) {
                    weight.value = Integer.MAX_VALUE.toFloat()
                } else {
                    weight.value = f
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