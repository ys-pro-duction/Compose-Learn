@file:OptIn(ExperimentalMaterial3Api::class)

package com.yogesh.composelearn.navigation.navigation_drawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yogesh.composelearn.navigation.navigation_drawer.ui.theme.ComposeLearnTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearnTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    "Drawer Title",
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.Gray))

                                Text(
                                    "Section 1",
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                NavigationDrawerItem(
                                    label = { Text("Item 1") },
                                    selected = true,
                                    onClick = { /* Handle click */ })
                                NavigationDrawerItem(
                                    label = { Text("Item 2") },
                                    selected = false,
                                    onClick = { /* Handle click */ })

                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.Gray))

                                Text(
                                    "Section 2",
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                NavigationDrawerItem(
                                    label = { Text("Settings") },
                                    selected = false,
                                    icon = {
                                        Icon(
                                            Icons.Outlined.Settings,
                                            contentDescription = null
                                        )
                                    },
                                    badge = { Text("20")
                                        Icon(Icons.Default.Create,null) }, // Placeholder
                                    onClick = { /* Handle click */ })
                                NavigationDrawerItem(
                                    label = { Text("Help and feedback") },
                                    selected = false,
                                    icon = {
                                        Icon(
                                            Icons.Default.Info,
                                            contentDescription = null
                                        )
                                    },
                                    onClick = { /* Handle click */ },
                                )
                                Spacer(Modifier.height(12.dp))
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.Gray))
                                NavigationDrawerItem(
                                    label = { Text("Close") },
                                    selected = false,
                                    onClick = {scope.launch { drawerState.close() }},
                                    icon = {Icon(Icons.Default.Close,null)})
                            }
                        }
                    }, drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Navigation Drawer Example") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            } else {
                                                drawerState.close()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                })
                        }) { innerPadding ->

                    }
                }
            }
        }
    }
}