package com.yogesh.composelearn.agsl

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.yogesh.composelearn.agsl.ui.theme.ComposeLearnTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        var rowOneWeight = remember { mutableFloatStateOf(1f) }
                        var rowTwoWeight = remember { mutableFloatStateOf(1f) }
                        Row (modifier = Modifier.fillMaxSize().weight(rowOneWeight.floatValue)){
                            CirclesWithSlider(modifier = Modifier.fillMaxSize(), rowOneWeight)
                            RippleShader(modifier = Modifier.fillMaxSize(), rowOneWeight)
                            VerticleGradient(modifier = Modifier.fillMaxSize(), rowOneWeight)
                            HorizontalGradient(modifier = Modifier.fillMaxSize(), rowOneWeight)
                            GradientColors(modifier = Modifier.fillMaxSize(), rowOneWeight)
                        }
                        Row (modifier = Modifier.fillMaxSize().weight(rowTwoWeight.floatValue)) {
                            DrawShapes(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            WavePattern(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            RadialGradient(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            SpiraleCircular(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            AnimatedShape(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            BitmapInShader(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            BlurSharpen(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                            OverlayPatternsImages(modifier = Modifier.fillMaxSize(), rowTwoWeight)
                        }
                        TouchCircle(modifier = Modifier.fillMaxSize(),1f)
                        InstStyleFilter(modifier = Modifier.fillMaxSize(),1f)
                        Noise(modifier = Modifier.fillMaxSize(),1f)
                        MarbleCloudTexture(modifier = Modifier.fillMaxSize(),1f)
                        GlowShadow(modifier = Modifier.fillMaxSize(),1f)
                        Illusion3D(modifier = Modifier.fillMaxSize(),1f)
                        StackingShaders(modifier = Modifier.fillMaxSize(),1f)
                        Cube(modifier = Modifier.fillMaxSize(),1f)
                    }
                }
            }
        }
    }
}