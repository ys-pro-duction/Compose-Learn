package com.yogesh.composelearn.navigation.simple.screen

const val DETAILS_DATA_KEY = "KEY"
sealed class Screen(val route: String) {
    object HOME: Screen(route = "HOME_R")
    object DETAIL: Screen("DETAIL_R/{$DETAILS_DATA_KEY}"){
        fun passValue(data: String): String {
            return DETAIL.route.replace("{$DETAILS_DATA_KEY}",data)
        }
    }
}