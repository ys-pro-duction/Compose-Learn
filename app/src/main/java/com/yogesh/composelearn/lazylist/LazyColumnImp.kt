@file:OptIn(ExperimentalLayoutApi::class)

package com.yogesh.composelearn.lazylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.yogesh.composelearn.btn_input_image.ui.theme.ComposeLearnTheme

class LazyColumnImp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    println(innerPadding)
//                    FlowRow {
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
    LazyColumn(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyHeader {
            Text("sdfsdfsdfdsfsdf")
        }
        itemsIndexed(repository.images) { idx: Int, image: ImageModel ->
            if (idx % 3 == 0) {
                HorizontalList()
            } else {
                ImageCard(image)
            }
        }
    }
}

@Composable
fun ImageCard(imageModel: ImageModel) {
    val imagepainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(imageModel.url).build()
    )
    Card(
        modifier = Modifier
            .height(300.dp)
            .width(200.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            Image(
                imagepainter,
                "",
                Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Spacer(
                Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black)
                            , startY = 300f
                        )
                    )
                    .fillMaxSize()

            )

            Text(
                imageModel.name, fontSize = 18.sp, color = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(16.dp)

            )
        }
    }
//    Column(modifier = Modifier.size(140.dp)
////        .fillMaxWidth()
//        .shadow(
//            8.dp, shape = CircleShape.copy(
//                CornerSize(50.dp)
//            )
//        )
//        .background(
//            color = Color.White, shape = CircleShape.copy(
//                CornerSize(50.dp)
//            )
//        )
//        .clickable { }
//        .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(imageModel.name, fontSize = MaterialTheme.typography.titleLarge.fontSize, modifier = Modifier)
//        Image(imagepainter,"",Modifier.aspectRatio(16f/9f))
}

@Composable
fun HorizontalList() {
    val repository = ImageRepository()
    LazyRow {
        itemsIndexed(repository.images) { idx: Int, image: ImageModel ->
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

