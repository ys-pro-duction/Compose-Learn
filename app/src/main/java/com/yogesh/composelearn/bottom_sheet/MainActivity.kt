@file:OptIn(ExperimentalMaterial3Api::class)

package com.yogesh.composelearn.bottom_sheet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogesh.composelearn.bottom_sheet.ui.theme.ComposeLearnTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BottomSheet(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun BottomSheet(modifier: Modifier = Modifier) {
        var peak by remember { mutableStateOf(true) }
        var bottomState = rememberStandardBottomSheetState(skipHiddenState = false)
        var sheetScaffoldState = rememberBottomSheetScaffoldState(bottomState)
        var coroutineScope = rememberCoroutineScope()
        BottomSheetScaffold(
            sheetContent = {
                BottomSheetContent(modifier = modifier)
            },
            scaffoldState = sheetScaffoldState,
            sheetPeekHeight = if (peak) BottomSheetDefaults.SheetPeekHeight else 0.dp,
            sheetDragHandle = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomSheetDefaults.DragHandle(
                        shape = RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp)
                    )
                    BottomSheetDefaults.DragHandle(height = 12.dp, shape = RoundedCornerShape(4.dp))
                    BottomSheetDefaults.DragHandle(
                        shape = RoundedCornerShape(topEnd = 2.dp, bottomEnd = 2.dp)
                    )
                }
            }) {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button({
                    coroutineScope.launch {
                        Log.d("TAG", "BottomSheet:  state ${bottomState.currentValue}, `${bottomState.hasExpandedState}`")
                        if(bottomState.currentValue == SheetValue.PartiallyExpanded){
//                            bottomState.show()
                            bottomState.expand()
                        }else {
                            bottomState.partialExpand()
                        }
                    }
                }) {
                    Text("Toggle Bottom Sheet")
                }
                Button({ peak = !peak }) {
                    Text("Peak Height")
                }
            }
        }
    }

    @Composable
    fun BottomSheetContent(modifier: Modifier = Modifier) {
        val nestedScrollConnection = rememberNestedScrollInteropConnection()
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(nestedScrollConnection)
            ) {
                items(35) {
                    Text(
                        text = "Item $it",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(
                                MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                    )
                }
            }
        }

    }
}