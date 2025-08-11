package com.yogesh.composelearn.agsl

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.Language

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Language("AGSL")
fun GradientRactangle(modifier: Modifier = Modifier) {
    val sliderValueRaw = remember { mutableStateOf(1f) }
    val sliderValue = animateFloatAsState(sliderValueRaw.value, animationSpec = tween(500, easing = CubicBezierEasing(0.18f,0.89f,0.32f,1.27f)))
    val shaderString = remember(sliderValue.value) {
        mutableStateOf("""
uniform float2 resolution;

half4 main(float2 fragCoord) {
    // Normalize coords (0–1)
    float2 uv = fragCoord / resolution;
    
    // Center origin, keep aspect ratio
    uv = uv * 2.0 - 1.0;
    uv.x *= resolution.x / resolution.y;
    
    // Distance from center
    float dist = length(uv);
    
    // Define two colors
    half3 color1 = half3(1.0, 0.5, 0.0); // red
    half3 color2 = half3(0.0, 0.0, 1.0); // blue
    
    // Blend colors based on distance
    float wave = sin(dist * ${sliderValue.value}) *0.5+0.5; // maps to 0..1
    half3 finalColor = mix(color1, color2, wave);
    
    return half4(finalColor, 1.0);
}
    """.trimIndent())
    }
//
    val shader = remember(sliderValue.value) { RuntimeShader(shaderString.value) }
    Canvas(modifier) {
        shader.setFloatUniform("resolution", size.width, size.height)
        drawRect(ShaderBrush(shader))

    }
    Slider(
        sliderValueRaw.value,
        { sliderValueRaw.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        valueRange = 1f..1000f
    )
}
