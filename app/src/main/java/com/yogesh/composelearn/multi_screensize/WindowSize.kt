package com.yogesh.composelearn.multi_screensize

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

data class WindowSize(val width: Int, val height: Int)

enum class Window {
    COMPACT, MEDIUM, LARGE
}

fun getScreenWidth(width: Int): Window {
    return when{
        width < 600 -> Window.COMPACT
        width < 840 -> Window.MEDIUM
        else -> Window.LARGE
    }
}

fun getScreenHeight(height: Int): Window {
    return when{
        height < 480 -> Window.COMPACT
        height < 900 -> Window.MEDIUM
        else -> Window.LARGE
    }
}

@Composable
fun rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    return WindowSize(
        width = configuration.screenWidthDp, height = configuration.screenHeightDp
    )
}