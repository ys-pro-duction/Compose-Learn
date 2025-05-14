package com.yogesh.composelearn.navigation.bottomsheet.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yogesh.composelearn.navigation.bottomsheet.screens.LoginScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.Screen
import com.yogesh.composelearn.navigation.bottomsheet.screens.SignupScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.ContactScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.HomeNavigationScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.HomeScreen
import com.yogesh.composelearn.navigation.bottomsheet.screens.home.SettingsScreen

const val HOME_ROUTE = "home_route"
const val AUTHENTICATION_ROUTE = "auth_route"
fun NavGraphBuilder.LoginNavGraph(navigationController: NavHostController) {
    navigation(route = AUTHENTICATION_ROUTE, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navigationController)
        }
        composable(Screen.SignupScreen.route) {
            SignupScreen(navigationController)
        }
    }
}
fun NavGraphBuilder.HomeNavigatoinScreenStandaloneGraph(navigationController: NavHostController) {
    navigation(route = HOME_ROUTE,startDestination = Screen.HomeNavigationScreen.route) {
        composable(Screen.HomeNavigationScreen.route) {
            HomeNavigationScreen(navigationController)
        }
    }
}


@Composable
fun MainNavGraph(navigationController: NavHostController) {
    NavHost(
        navigationController,
        startDestination = if (LocalContext.current.getSharedPreferences(
                    "LOGIN",
                    Context.MODE_PRIVATE
                ).getBoolean("login", false)
        ) HOME_ROUTE else AUTHENTICATION_ROUTE
    ) {
        this.LoginNavGraph(navigationController)
        this.HomeNavigatoinScreenStandaloneGraph(navigationController)
    }
}

