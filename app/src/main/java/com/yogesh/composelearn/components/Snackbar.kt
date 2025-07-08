package com.yogesh.composelearn.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun SnackBar(modifier: Modifier = Modifier.fillMaxSize()) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) {}
    scope.launch {
        snackbarHostState.showSnackbar(
            "hii", "ok", false, SnackbarDuration.Short
        ).let { result ->
            when (result) {
                SnackbarResult.Dismissed -> {
                    Toast.makeText(context, "Snackbar dismissed", Toast.LENGTH_SHORT).show()
                }
                SnackbarResult.ActionPerformed -> {
                    Toast.makeText(context, "Snackbar action performed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}