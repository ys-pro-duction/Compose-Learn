package com.yogesh.composelearn.agsl

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
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
                        CirclesWithSlider(modifier = Modifier.fillMaxSize(),1f)
                        RippleShader(modifier = Modifier.fillMaxSize(),1f)
                        VerticleGradient(modifier = Modifier.fillMaxSize(),1f)
                        HorizontalGradient(modifier = Modifier.fillMaxSize(),1f)
                        GradientColors(modifier = Modifier.fillMaxSize(),1f)
                        DrawShapes(modifier = Modifier.fillMaxSize(),1f)
                        WavePattern(modifier = Modifier.fillMaxSize(),1f)
                        RadialGradient(modifier = Modifier.fillMaxSize(),1f)
                    }
                }
            }
        }
    }
}