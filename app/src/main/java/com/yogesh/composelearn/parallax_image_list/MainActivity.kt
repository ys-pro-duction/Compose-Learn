package com.yogesh.composelearn.parallax_image_list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yogesh.composelearn.R
import com.yogesh.composelearn.parallax_image_list.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }


    @Composable
    fun MainScreen(modifier: Modifier = Modifier) {
        val itemsList1 = remember { (1..10).toList() }
        val itemsList2 = remember { (11..20).toList() }
        val lazyListState = rememberLazyListState()
        var yOffest by remember { mutableStateOf(0f) }
        var yMove = animateFloatAsState(yOffest, tween(1))
        val nestedScroll = remember(Unit) {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset, source: NestedScrollSource
                ): Offset {

                    if (lazyListState.layoutInfo.visibleItemsInfo.first().index == 0
                        ||
                        lazyListState.layoutInfo.visibleItemsInfo.last().index == 21
                    ) yOffest += available.y * 0.1f
                    return super.onPreScroll(available, source)
                }
            }
        }
        val textModifier = remember { Modifier
            .padding(horizontal = 8.dp)
            .background(
                Color.Gray, RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .padding(8.dp) }
        LazyColumn(
            modifier = modifier.nestedScroll(nestedScroll),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState
        ) {

            items(itemsList1, key = {
                Log.d("TAG", "MainScreen: $it")
                return@items it
            }) {
                Text(
                    "item $it", modifier = textModifier
                )
            }
            item(key = 100) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painterResource(R.drawable.pthree),
                        null,
                        modifier = Modifier
                            .fillMaxWidth().scale(1.2f)
                            .graphicsLayer(translationY = yMove.value),
                        contentScale = ContentScale.FillWidth
                    )
                    Image(
                        painterResource(R.drawable.ptwo),
                        null,
                        modifier = Modifier
                            .fillMaxWidth().scale(1.2f)
                            .graphicsLayer(translationY = yMove.value * -0.8f),
                        contentScale = ContentScale.FillWidth
                    )
                    Image(
                        painterResource(R.drawable.pone),
                        null,
                        modifier = Modifier
                            .fillMaxWidth().scale(1.2f)
                            .graphicsLayer(translationY = yMove.value * -0.4f),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            items(itemsList2, key = {    return@items it}) {
                Text(
                    "item $it", modifier = textModifier
                )
            }
        }
    }
}