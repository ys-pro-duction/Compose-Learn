package com.yogesh.composelearn.multi_screensize

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yogesh.composelearn.multi_screensize.ui.theme.ComposeLearnTheme

@Composable
fun YTScreen(windowSize: WindowSize) {
    ComposeLearnTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White) { innerPadding ->
            val videoDataRepository = remember { mutableStateOf(DataRepository()) }
            val videoData = remember { videoDataRepository.value.getData(50) }
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(videoData, key = { it.id }) { video ->
                    VideoItem(videoData = video, windowSize = windowSize)
                }
            }
        }
    }
}