package com.yogesh.composelearn.navigation.bottomsheet.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("signup_screen")
    object HomeScreen: Screen("home_screen")
    object HomeNavigationScreen: Screen("home_nav_screen")
    object ContactScreen: Screen("contact_screen")
    object SettingsScreen: Screen("settings_screen")
}