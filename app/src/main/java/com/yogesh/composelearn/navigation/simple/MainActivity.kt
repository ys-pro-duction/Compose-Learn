package com.yogesh.composelearn.navigation.simple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yogesh.composelearn.navigation.simple.screen.DETAILS_DATA_KEY
import com.yogesh.composelearn.navigation.simple.screen.DetailsScreen
import com.yogesh.composelearn.navigation.simple.screen.HomeScreen
import com.yogesh.composelearn.navigation.simple.screen.Screen
import com.yogesh.composelearn.navigation.simple.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            navController = rememberNavController()
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    println(innerPadding)
                    NavHost(navController, startDestination = Screen.HOME.route) {
                        composable(route = Screen.HOME.route) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screen.DETAIL.route, arguments = listOf(
                                navArgument(
                                    DETAILS_DATA_KEY
                                ) {
                                    type = NavType.StringType
                                })
                        ) {

                            DetailsScreen(navController, it.arguments?.getString(DETAILS_DATA_KEY))
                        }
                    }
                }

            }
        }
    }
}
