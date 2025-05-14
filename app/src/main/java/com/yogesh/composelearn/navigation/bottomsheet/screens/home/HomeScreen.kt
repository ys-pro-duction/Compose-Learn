package com.yogesh.composelearn.navigation.bottomsheet.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
@Preview(showBackground = true)
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().background(color = Color.Cyan)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Home", fontSize = TextUnit(50f, TextUnitType.Sp))
            Spacer(Modifier.height(24.dp))
            Button({
                TODO("IMP")
            }) {
                Text("go to contact")
            }
            Spacer(Modifier.height(12.dp))

        }
    }
}