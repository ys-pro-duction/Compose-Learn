package com.yogesh.composelearn.navigation.simple.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Home", fontSize = TextUnit(50f, TextUnitType.Sp))
            Spacer(Modifier.height(12.dp))
            var inputValue = remember { mutableStateOf("") }
            TextField(inputValue.value,{newValue->
                inputValue.value = newValue
            })
            Button({
                navController.navigate(Screen.DETAIL.passValue(inputValue.value))
            }) {
                Text("Go to detail's page")
            }
        }
    }
}