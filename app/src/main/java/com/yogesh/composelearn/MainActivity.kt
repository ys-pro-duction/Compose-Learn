package com.yogesh.composelearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.yogesh.composelearn.lazylist.LazyColumnImp
import com.yogesh.composelearn.navigation.simple.MainActivity
import com.yogesh.composelearn.rotatingKnobe.VolumeKnobe
import com.yogesh.composelearn.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(modifier: Modifier = Modifier) {
    val scrollState = ScrollableState{it}
    Column(modifier.fillMaxSize().scrollable(state = scrollState, orientation = Orientation.Vertical), horizontalAlignment = Alignment.CenterHorizontally) {
        intentButton("Button Input Image",com.yogesh.composelearn.btn_input_image.MainActivity::class.java,LocalContext.current)
        intentButton("Lazy list",LazyColumnImp::class.java,LocalContext.current)
        intentButton("Simple Navigation",MainActivity::class.java,LocalContext.current)
        intentButton("Bottom Navigation",com.yogesh.composelearn.navigation.bottomsheet.MainActivity::class.java,LocalContext.current)
        intentButton("Search widget",com.yogesh.composelearn.search_widget.MainActivity::class.java,LocalContext.current)
        intentButton("Shimmer effect",com.yogesh.composelearn.animatedShimmereffect.MainActivity::class.java,LocalContext.current)
        intentButton("Splash screen",com.yogesh.composelearn.splashScreen.MainActivity::class.java,LocalContext.current)
        intentButton("Rotating volume knob", VolumeKnobe::class.java,LocalContext.current)
        intentButton("Timer", com.yogesh.composelearn.timer.MainActivity::class.java,LocalContext.current)
        intentButton("Botom sheet", com.yogesh.composelearn.bottom_sheet.MainActivity::class.java,LocalContext.current)
    }
}
@Composable
fun intentButton(name: String, activity: Class<*>,context: Context) {
    Button({ context.startActivity(Intent(context,activity))}
        , modifier = Modifier.fillMaxWidth(0.8f)) { Text(name) }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ComposeLearnTheme {
        Greeting2()
    }
}