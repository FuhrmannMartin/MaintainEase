package com.example.maintainease.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.viewModel.OverviewScreenViewModel
import com.example.maintainease.widgets.ListOfMaintenanceTask
import com.example.maintainease.widgets.SimpleBottomAppBar
import com.example.maintainease.widgets.SimpleTopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun OverviewScreen(navController: NavController) {
    val context = LocalContext.current
    val overviewScreenViewModel: OverviewScreenViewModel = viewModel(factory = InjectorUtils.provideOverviewScreenViewModelFactory(context))
    val maintenances by overviewScreenViewModel.maintenances.collectAsState()


    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "MaintainEase")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        ListOfMaintenanceTask(
            modifier = Modifier.padding(innerPadding),
            maintenance = maintenances,
            navController = navController,
            viewModel = overviewScreenViewModel
        )
    }
}