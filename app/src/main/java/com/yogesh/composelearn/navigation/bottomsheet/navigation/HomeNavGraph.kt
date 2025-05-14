package com.yogesh.composelearn.navigation.bottomsheet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yogesh.composelearn.navigation.bottomsheet.screens.Screen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.ContactScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.HomeScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.SettingsScreen

@Composable
fun HomeNavGraph(navigationController: NavHostController) {
    NavHost(
        navigationController, startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navigationController)
        }
        composable(Screen.ContactScreen.route, arguments = listOf()) {
            ContactScreen(navigationController)
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(navigationController)
        }
    }
}