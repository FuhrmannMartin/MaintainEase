package com.example.maintainease.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.viewModel.DetailScreenViewModel
import com.example.maintainease.widgets.MaintenanceTask
import com.example.maintainease.widgets.SimpleBottomAppBar
import com.example.maintainease.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    taskId: Int,
    navController: NavController
) {
    val context = LocalContext.current

    val detailScreenViewModel: DetailScreenViewModel = viewModel(
        factory = InjectorUtils.provideDetailScreenViewModelFactory(context, taskId)
    )
    val maintenanceTask by detailScreenViewModel.maintenance.collectAsState()
    val assignee by detailScreenViewModel.assignee.collectAsState()

    val currentUserName by detailScreenViewModel.currentUserName.collectAsState()

    var dropDownExpanded by remember { mutableStateOf(false) }
    var selectedStatusChange by remember {  mutableStateOf("open")  }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopAppBar(
                title = maintenanceTask?.maintenance?.title ?: "Maintenance Task Details",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Icon")
                    }
                },
                onLogoutClick = {
                    navController.navigate("login")
                }
            )
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                maintenanceTask?.let { maintenanceTask ->
                    MaintenanceTask(maintenanceWithAssignee = maintenanceTask,
                        navController = navController,
                        onItemClick = {})
                }
            }
            item {
                if (assignee?.id != null) {
                    Column {
                        Text(text = "Change Status:", modifier = Modifier.padding(start = 16.dp))
                        Box(modifier = Modifier.padding(15.dp, bottom = 0.dp)) {
                            Button(
                                onClick = { dropDownExpanded = true },
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Text(selectedStatusChange)
                            }
                            DropdownMenu(
                                expanded = dropDownExpanded,
                                onDismissRequest = { dropDownExpanded = false }) {
                                DropdownMenuItem(text = { Text("open") }, onClick = {
                                    selectedStatusChange = "open"
                                    dropDownExpanded = false
                                })
                                DropdownMenuItem(text = { Text("working") }, onClick = {
                                    selectedStatusChange = "working"
                                    dropDownExpanded = false
                                })
                                DropdownMenuItem(text = { Text("Done") }, onClick = {
                                    selectedStatusChange = "done"
                                    dropDownExpanded = false
                                })
                                DropdownMenuItem(text = { Text("cancel") }, onClick = {
                                    selectedStatusChange = "cancelled"
                                    dropDownExpanded = false
                                })
                            }
                        }
                        detailScreenViewModel.viewModelScope.launch {
                            try {
                                selectedStatusChange.let { detailScreenViewModel.updateStatus(it) }

                            } catch (e: Exception) {
                                // Handle any exceptions if necessary

                            }
                        }
                    }
                }
            }

            item {
                Text(text = "Comments:", modifier = Modifier.padding(start = 16.dp))
            }
            item {
                Column(modifier = Modifier.padding(8.dp)) {
                    val comments = maintenanceTask?.maintenance?.comments

                    if (comments != null) {
                        if (comments.isNotEmpty()) {
                            for (comment in comments) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 6.dp),
                                    shape = ShapeDefaults.ExtraLarge,
                                    elevation = CardDefaults.cardElevation(10.dp)
                                ) {
                                    Text(text = comment,
                                            modifier = Modifier
                                            .padding(16.dp))
                                }
                            }
                        } else {
                            println("The list is empty.")
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    var comment by remember { mutableStateOf("") }

                    TextField(
                        value = comment,
                        onValueChange = { comment = it },
                        label = { Text("Enter your comment") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            detailScreenViewModel.viewModelScope.launch {
                                detailScreenViewModel.addComment(
                                    currentUserName + ", " + detailScreenViewModel.getCurrentTimestamp() + ":" + System.lineSeparator() + comment
                                )
                            }
                        },
                    ) {
                        Text(text = "Add new comment!")
                    }
                }
            }
            item {
                Box(modifier = Modifier.padding(start = 16.dp)) {
                    if (assignee?.id == null) {
                        Button(
                            onClick = { detailScreenViewModel.viewModelScope.launch { detailScreenViewModel.assignToMe() } },
                        ) {
                            Text(text = "Assign to me")
                        }
                    } else {
                        maintenanceTask?.let { task ->
                            Button(
                                onClick = {
                                    detailScreenViewModel.viewModelScope.launch {
                                        detailScreenViewModel.deleteTheTask(
                                            task.maintenance,
                                            navController
                                        )
                                    }
                                }
                            ) {
                                Text(color = Color.Red, text = "Delete Task")
                            }
                        }
                    }
                }
            }
        }
    }
}
