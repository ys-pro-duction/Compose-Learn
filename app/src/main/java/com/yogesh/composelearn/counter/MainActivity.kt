package com.yogesh.composelearn.counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogesh.composelearn.counter.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var count by remember { mutableIntStateOf(0) }
                    AnimatedCounter(
                        modifier = Modifier.padding(innerPadding),
                        count = count,
                        onIncrement = {
                            count++
                        },
                        onDecrement = {
                            count--
                        })
                }
            }
        }
    }

    @Composable
    fun AnimatedCounter(
        modifier: Modifier, count: Int, onIncrement: () -> Unit, onDecrement: () -> Unit
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var oldCount by remember { mutableStateOf(count) }
            SideEffect {
                oldCount = count
            }
            Row {
                val c = count.toString()
                val oc = oldCount.toString()
                for (i in c.indices) {
                    val char = if (c[i] == oc.getOrNull(i)) oc[i].toString()
                    else c[i].toString()

                    AnimatedContent(char, transitionSpec = {
                        if (count > oldCount) (scaleIn(
                            initialScale = .5f,
                            animationSpec = tween(700)
                        ) + fadeIn() + slideInVertically(
                            animationSpec = spring(
                                0.5f, 200f
                            )
                        ) { it }).togetherWith((slideOutVertically { -it } + fadeOut()))
                        else (fadeIn() + slideInVertically { -it }).togetherWith(
                            (slideOutVertically(
                                animationSpec = tween(100)
                            ) { it } + fadeOut()))
                    }) {
                        Text(it, fontSize = 50.sp)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button({ onDecrement() }) { Text("--") }
                Button({ onIncrement() }) { Text("++") }
            }
        }
    }

}