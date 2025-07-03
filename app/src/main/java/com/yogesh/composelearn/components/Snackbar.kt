package com.yogesh.composelearn.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun SnackBar(modifier: Modifier = Modifier.fillMaxSize()) {

    var snackbarHostState = remember { SnackbarHostState() }
    var scope = rememberCoroutineScope()
    Scaffold(snackbarHost = {

        SnackbarHost(snackbarHostState)
    }) {}
    scope.launch {
        snackbarHostState.showSnackbar(
            "hi", "ok", true, SnackbarDuration.Short
        )

    }
}