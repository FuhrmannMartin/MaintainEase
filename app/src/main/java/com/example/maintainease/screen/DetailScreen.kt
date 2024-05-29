package com.example.maintainease.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.viewModel.DetailScreenViewModel
import com.example.maintainease.widgets.CustomDivider
import com.example.maintainease.widgets.MaintenanceTask
import com.example.maintainease.widgets.SimpleBottomAppBar
import com.example.maintainease.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch


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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopAppBar(
                title = "",// maintenanceTask?.maintenance?.title ?: "Maintenance Task Details",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Icon")
                    }
                }
            )
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            maintenanceTask?.let { maintenanceTask ->
                MaintenanceTask(maintenanceWithAssignee = maintenanceTask, navController = navController)
            }
            CustomDivider()
            Box(modifier = Modifier.padding(start = 16.dp)) {
                if (assignee?.id == null) {
                    Button(
                        onClick = { detailScreenViewModel.viewModelScope.launch { detailScreenViewModel.assignToMe() } },
                    ) {
                        Text(text = "Assign to me")
                    }
                } else {
                    Box(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Assigned to ${assignee?.name}!")
                            Spacer(modifier = Modifier.width(16.dp)) // Add some space between the button and the text
                        }
                    }
                }
            }
            CustomDivider()
            Text(text = "Comments:", modifier = Modifier.padding(start = 16.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                val comments = maintenanceTask?.maintenance?.comments

                if (comments != null) {
                    if (comments.isNotEmpty()) {
                        for (comment in comments) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                shape = ShapeDefaults.Large,
                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {
                                Text(text = comment)
                            }
                        }
                    } else {
                        println("The list is empty.")
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                var comment by remember { mutableStateOf("") }

                TextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Enter your comment") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { detailScreenViewModel.viewModelScope.launch { detailScreenViewModel.addComment(comment) } },
                ) {
                    Text(text = "Add new comment!")
                }
            }
        }
    }
}
