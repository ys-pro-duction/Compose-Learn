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
fun ColumnScope.MarbleCloudTexture(modifier: Modifier = Modifier, f: Float) {
    val shadderCode = """
uniform float uTime;
uniform vec2 resolution;
uniform shader uBitmap;
uniform vec2 uTouch;

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453123);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    
    float a = hash(i);
    float b = hash(i + vec2(1.0, 0.0));
    float c = hash(i + vec2(0.0, 1.0));
    float d = hash(i + vec2(1.0, 1.0));
    
    vec2 u = f * f * (3.0 - 2.0 * f); // smooth interpolation
    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}
float fbm(vec2 p) {
    float value = 0.0;
    float scale = 0.5;
    for (int i = 0; i < 5; i++) {
        value += noise(p) * scale;
        p *= 2.0;
        scale *= 0.5;
    }
    return value;
}


half4 main(vec2 fragCoord) {
    vec2 uv = fragCoord / resolution * uTouch[1]/100.0; // scale texture
    
    // marble
    float n = noise(uv + uTime * 1.0); // wave speed
    float marble = sin(uv.x * 10.0 + n * 2.0);
    
    // cloud
    float c = fbm(uv + n * 0.05);
    vec3 color = vec3(c); // grayscale clouds
    
    vec3 col = mix(vec3(0.8, 0.8, 0.7), vec3(0.4, 0.4, 0.5), n * 0.5 + 0.5);
    return mix(half4(color, 1.0),half4(0.1,0.1,0.8,1.0),step(uTouch[0]/1000.0,1-c));
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