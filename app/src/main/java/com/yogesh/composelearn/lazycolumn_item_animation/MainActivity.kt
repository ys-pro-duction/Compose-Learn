@file:OptIn(ExperimentalFoundationApi::class)

package com.yogesh.composelearn.lazycolumn_item_animation

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogesh.composelearn.lazycolumn_item_animation.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun MainScreen(modifier: Modifier = Modifier) {
        val sensoreManager = getSystemService(SensorManager::class.java)
        val sensors =
            remember { mutableStateOf(sensoreManager.getSensorList(Sensor.TYPE_ALL).subList(0,12).toList()) }
        LazyColumn(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(sensors.value,key = {i, it -> it.hashCode()}) { idx, it ->
                Text(
                    "${idx + 1}. ${it.type} ${it.name}", modifier = Modifier
                        .padding(horizontal = 16.dp)
//                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.onPrimary,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                       .animateItem()
                )
            }


            item {
                Button({
                    sensors.value = sensors.value.shuffled()
                },modifier = Modifier) {
                    Text("Suffle Sensors", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}