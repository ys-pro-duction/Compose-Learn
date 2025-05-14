package com.yogesh.composelearn.btn_input_image

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.yogesh.composelearn.R
import com.yogesh.composelearn.btn_input_image.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLearnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        textFeildsExample()
                        googleLogIn()
                        GoogleButton {}
                        NetworkImageLoad()
                    }

                }
            }
        }
    }
}

@Composable
fun textFeildsExample(modifier: Modifier = Modifier) {
    var tfValue by remember { mutableStateOf("") }
    TextField(tfValue,
        onValueChange = { newText ->
            tfValue = newText
        },
        maxLines = 4,
        minLines = 1,
        isError = false,
        colors = TextFieldDefaults.colors(focusedTextColor = Color.Blue),
        keyboardOptions = KeyboardOptions(
            KeyboardCapitalization.None,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Search
        ),
        shape = MaterialTheme.shapes.large,
        label = { Text(tfValue) },
        leadingIcon = {
            Icon(
                painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )
        },
        trailingIcon = {
            Icon(
                painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 10.dp)
            )
        })
    BasicTextField(tfValue, onValueChange = { newText ->
        tfValue = newText
    })
    OutlinedTextField(
        tfValue,
        onValueChange = { newText ->
            tfValue = newText
        },
        modifier = Modifier,
        placeholder = { Text("Email address") },
        label = { Text("email") },
        leadingIcon = {
            Icon(
                painterResource(android.R.drawable.ic_dialog_email), null
            )
        },
        trailingIcon = {
            Icon(
                painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                null,
                Modifier.clickable { tfValue = "" })
        })
}

@Composable
fun googleLogIn(modifier: Modifier = Modifier) {
    var logining by remember { mutableStateOf(false) }
    Button(
        {
            logining = !logining
        },
        border = BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.buttonColors(Color.White),
        shape = MaterialTheme.shapes.extraSmall,
        contentPadding = PaddingValues(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
        modifier = Modifier.animateContentSize(
            tween(
                durationMillis = 300,
                easing = androidx.compose.animation.core.EaseOutBack
            )
        )
    ) {
        Row(
            modifier = Modifier.animateContentSize(
                tween(
                    durationMillis = 300,
                    easing = EaseOutBack,
                )
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.google_icon),
                null,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
            Text(
                if (logining) "Creating Account..." else "Sigin With Google",
                color = Color.DarkGray,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.padding(start = 8.dp)
            )
            if (logining) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(
                    color = Color.DarkGray,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = "Sign Up with Google",
    loadingText: String = "Creating Account...",
    icon: Int = R.drawable.google_icon,
    shape: Shape = MaterialTheme.shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClicked: () -> Unit,
) {
    var clicked by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                clicked = !clicked
                onClicked()
            },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) loadingText else text)
            if (clicked) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@Composable
fun NetworkImageLoad(modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://camo.githubusercontent.com/fd890f47fdaf39f0db67fc9cd8950027949eac6cce9aa444bb42cba65ec5fb37/68747470733a2f2f692e706f7374696d672e63632f4878634c435239622f5369676e2d75702d776974682d676f6f676c652d627574746f6e2e706e67")
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "null",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(200.dp).clip(CircleShape).aspectRatio(1f).border(10.dp, Brush.linearGradient(
            listOf(Color.Blue, Color.Cyan, Color.Green)
        ), CircleShape))
    val image = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
        .data("https://camo.githubusercontent.com/fd890f47fdaf39f0db67fc9cd8950027949eac6cce9aa444bb42cba65ec5fb37/68747470733a2f2f692e706f7374696d672e63632f4878634c435239622f5369676e2d75702d776974682d676f6f676c652d627574746f6e2e706e67")
        .crossfade(true)
        .build())

    Image(image,null,modifier = Modifier.padding(20.dp).size(100.dp).shadow(12.dp))
}

@Composable
@Preview
fun preview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NetworkImageLoad()
        }

    }
}