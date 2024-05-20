package com.example.maintainease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.viewModel.NewTaskScreenViewModel
import com.example.maintainease.widgets.SimpleBottomAppBar
import com.example.maintainease.widgets.SimpleTopAppBar

@Composable
fun NewTaskScreen(
    navController: NavController
)
{
    val context = LocalContext.current
    val newTaskScreenViewModel: NewTaskScreenViewModel =
        viewModel(factory = InjectorUtils.provideNewTaskScreenViewModelFactory(context))
    val maintenances by newTaskScreenViewModel.maintenances.collectAsState()

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "MaintainEase")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "PLACEHOLDER")
        }
    }
}