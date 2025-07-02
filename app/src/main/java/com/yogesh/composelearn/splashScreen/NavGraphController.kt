package com.yogesh.composelearn.splashScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class Routes {
    companion object {
        const val SPLASH_SCREEN = "splash_screen"
        const val HOME_SCREEN = "home_screen"
    }
}

@Composable
fun bindRoutesToNavHostController(navHostController: NavHostController) {
    NavHost(navHostController, Routes.SPLASH_SCREEN) {
        composable(Routes.SPLASH_SCREEN) {
            SplashScreen(navHostController)
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navHostController)
        }
    }
}