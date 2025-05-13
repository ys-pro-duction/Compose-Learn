package com.yogesh.composelearn.navigation.simple.screen

sealed class Screen(val route: String) {
    object HOME: Screen(route = "HOME_R")
    object DETAIL: Screen("DETAIL_R")
}