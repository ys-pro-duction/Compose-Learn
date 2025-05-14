package com.yogesh.composelearn.navigation.bottomsheet.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yogesh.composelearn.navigation.bottomsheet.navigation.AUTHENTICATION_ROUTE
import com.yogesh.composelearn.navigation.bottomsheet.navigation.HOME_ROUTE

@Composable
@Preview(showBackground = true)
fun LoginScreen(navController: NavHostController = rememberNavController()) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Login", fontSize = TextUnit(50f, TextUnitType.Sp))
            Spacer(Modifier.height(24.dp))
            Button({
                navController.navigate(HOME_ROUTE){
                    popUpTo(AUTHENTICATION_ROUTE)
                }
            }) {
                Text("Login")
            }
            Spacer(Modifier.height(12.dp))
            Text("Don't have an account",Modifier.clickable {
                navController.navigate(Screen.SignupScreen.route)
            })
        }
    }
}