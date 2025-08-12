package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun VerticleGradient(modifier: Modifier = Modifier) {
    val shadderCode = """
        uniform float2 res;
        uniform half time;
        half4 main( float2 coord){
            float2 uv = coord / res;
            half3 red = half3(1.0,0.0,0.0);
            half3 blue = half3(0.0,0.0,1.0);
            float fac = sin(uv.y*50.0+time);
            return half4(mix(red,blue,fac),1.0);
            
        }
    """.trimIndent()

    val time = remember { mutableStateOf(0f) }
    val shader = remember { RuntimeShader(shadderCode) }
    Canvas(modifier = modifier) {
        drawIntoCanvas {
            shader.setFloatUniform("res",size.width,size.height)
            shader.setFloatUniform("time",time.value)
            drawRect(ShaderBrush(shader))
            it.drawRect(size.toRect(), Paint().apply { this.asFrameworkPaint().shader = shader })
        }
    }
    LaunchedEffect(Unit) {
        while (true){
            delay(17)
            time.value += 0.16f
        }
    }
}