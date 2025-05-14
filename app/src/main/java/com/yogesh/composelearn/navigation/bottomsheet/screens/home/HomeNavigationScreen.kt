package com.yogesh.composelearn.navigation.bottomsheet.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yogesh.composelearn.navigation.bottomsheet.navigation.HomeNavGraph
import com.yogesh.composelearn.navigation.bottomsheet.screens.Screen

@Composable
@Preview(showBackground = true)
fun HomeNavigationScreen(rootNavControllerr: NavHostController = rememberNavController()) {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        NavigationBar {
            BottomBar(navController)
        }
    }) { paddingValues: PaddingValues ->
        HomeNavGraph(navController)
        println(paddingValues)
    }
}

@Composable
fun RowScope.BottomBar(navController: NavHostController) {
    val bottomNavController by navController.currentBackStackEntryAsState()
    val destination = bottomNavController?.destination
    val bottomNavItems = listOf(
        BottomNavItem(
            label = "Home", icon = Icons.Filled.Home, route = Screen.HomeScreen.route
        ),
        BottomNavItem(
            label = "Contact", icon = Icons.Filled.Person, route = Screen.ContactScreen.route
        ),
        BottomNavItem(
            label = "Settings", icon = Icons.Filled.Settings, route = Screen.SettingsScreen.route
        ),
    )

    bottomNavItems.forEach { navItem ->
        AddItem(navItem, navController, destination)
    }
}

@Composable
fun RowScope.AddItem(
    navItem: BottomNavItem, navController: NavHostController, destination: NavDestination?
) {
    NavigationBarItem(
        destination?.route == navItem.route,
        onClick = {
            navController.navigate(navItem.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
//            if (navItem.route == Screen.HomeScreen.route) {
//                navController.popBackStack(Screen.HomeScreen.route, false)
//            } else {
//
//            }
        },
        icon = { Icon(navItem.icon, "") },
        label = { Text(navItem.label) },
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = LocalContentColor.current.copy(0.6f),
            unselectedTextColor = LocalContentColor.current.copy(0.6f)
        )
    )
}


data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
