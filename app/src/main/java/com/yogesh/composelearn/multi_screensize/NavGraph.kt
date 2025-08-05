package com.yogesh.composelearn.multi_screensize

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun setupNavGraph(navController: NavHostController, windowSize: WindowSize) {
    NavHost(navController, startDestination = "YTScreen"){
        composable("YTScreen") {
            YTScreen(windowSize)
        }
    }
}