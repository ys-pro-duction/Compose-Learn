@file:OptIn(ExperimentalLayoutApi::class)

package com.yogesh.composelearn.lazylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.yogesh.composelearn.ui.theme.ComposeLearnTheme

class LazyColumnImp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    println(innerPadding)
                    val repository = ImageRepository()
//                    FlowRow  {
//                        repository.images.forEach {
//                            ImageCard(it)
//                        }
//                    }
                    VerticleList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticleList(modifier: Modifier = Modifier) {
    val repository = ImageRepository()

    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        stickyHeader {
            Text("sdfsdfsdfdsfsdf")
        }
        itemsIndexed(repository.images){idx: Int,image: ImageModel  ->
            if (idx % 3 == 0) {
                HorizontalList()
            } else {
                ImageCard(image)
            }
        }
    }
}

@Composable
fun ImageCard(imageModel: ImageModel){
    val imagepainter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(imageModel.url).build())
    Column(modifier = Modifier.size(140.dp)
//        .fillMaxWidth()
        .shadow(
            8.dp, shape = CircleShape.copy(
                CornerSize(50.dp)
            )
        )
        .background(
            color = Color.White, shape = CircleShape.copy(
                CornerSize(50.dp)
            )
        )
        .clickable { }
        .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(imageModel.name, fontSize = MaterialTheme.typography.titleLarge.fontSize, modifier = Modifier)
        Image(imagepainter,"",Modifier.aspectRatio(16f/9f))
    }
}
@Composable
fun HorizontalList(){
    val repository = ImageRepository()
    LazyRow {
        itemsIndexed(repository.images){idx: Int,image: ImageModel  ->
            ImageCard(image)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLearnTheme {
        VerticleList()
    }
}

