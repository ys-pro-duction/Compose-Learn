package com.yogesh.composelearn.splashScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yogesh.composelearn.R

@Composable
@Preview(showBackground = true)
fun SplashScreen(navHostController: NavHostController = rememberNavController()) {
    val alphaState = remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (alphaState.value) 1f else 0f, animationSpec = tween(3000)
    )
    Splash(alphaAnimation.value)
    LaunchedEffect(true) {
        alphaState.value = true
        // Simulate a delay for splash screen
        kotlinx.coroutines.delay(4000)
        navHostController.popBackStack()
        navHostController.navigate(Routes.HOME_SCREEN)
    }
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .alpha(alpha)
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.google_icon), "", modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}