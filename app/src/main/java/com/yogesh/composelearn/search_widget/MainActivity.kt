package com.yogesh.composelearn.search_widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yogesh.composelearn.R
import com.yogesh.composelearn.search_widget.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppBar(viewModel = viewModel,innerPadding.calculateTopPadding())

                }
            }
        }
    }
}

@Composable
fun AppBar(viewModel: SearchViewModel, topPadding: Dp) {
    val appBarType = viewModel.appBar.collectAsState()
    if (appBarType.value == APP_BAR.NORMAL) {
        TopAppNormalBar(topPadding, openSearchBox = {viewModel.updateAppBar(APP_BAR.SEARCH)})
    } else {
        TopSearchAppBar(
            topPadding,
            viewModel = viewModel,
            onCloseInput = { viewModel.updateAppBar(APP_BAR.NORMAL) },
            updateSearchText = { viewModel.updateText(it) },
            onSearchInput = {
                println("Search: $it")
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppNormalBar(topPadding: Dp, openSearchBox: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Home")
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            navigationIcon = {
                Icon(painterResource(R.drawable.ic_launcher_foreground), null)
            },
            actions = {
                IconButton(openSearchBox) {
                    Icon(painterResource(R.drawable.search), null)
                }
            })
    }) {
        println(it)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchAppBar(
    topPadding: Dp,
    viewModel: SearchViewModel,
    onCloseInput: () -> Unit,
    updateSearchText: (String) -> Unit,
    onSearchInput: (String) -> Unit
) {
    val text = viewModel.searchText.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding)
    ) {
        TextField(
            value = text.value,
            onValueChange = { updateSearchText(it) },
            leadingIcon = { Icon(painterResource(R.drawable.search), null) },
            trailingIcon = {
                IconButton({
                    if (text.value.isEmpty()) {
                        onCloseInput()
                    } else updateSearchText("")
                }) { Icon(painterResource(R.drawable.close), null) }
            },
            placeholder = { Text("Search here...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchInput(text.value) })
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    ComposeLearnTheme {
//        TopAppNormalBar()
        TopSearchAppBar(0.dp, viewModel(SearchViewModel::class.java), {}, {}, {})
    }
}