package com.example.maintainease.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.data.Maintenance
import com.example.maintainease.viewModel.OverviewScreenViewModel
import com.example.maintainease.widgets.ListOfMaintenanceTask
import com.example.maintainease.widgets.SimpleBottomAppBar
import com.example.maintainease.widgets.SimpleTopAppBar


@Composable
fun OverviewScreen(navController: NavController) {
    val context = LocalContext.current
    val overviewScreenViewModel: OverviewScreenViewModel =
        viewModel(factory = InjectorUtils.provideOverviewScreenViewModelFactory(context))
    val openMaintenances by overviewScreenViewModel.openMaintenances.collectAsState()
    val inProgressMaintenances by overviewScreenViewModel.inProgressMaintenances.collectAsState()
    val doneMaintenances by overviewScreenViewModel.doneMaintenances.collectAsState()
    val cancelledMaintenances by overviewScreenViewModel.cancelledMaintenances.collectAsState()

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "MaintainEase")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Maintenance Tasks",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall // Use style instead of fontSize
            )
            MaintenanceBox(
                name =  "Open",
                items = openMaintenances,
                navController =  navController,
                viewModel = overviewScreenViewModel
            )
            MaintenanceBox(
                name =  "In Progress",
                items = inProgressMaintenances,
                navController =  navController,
                viewModel = overviewScreenViewModel
            )
            MaintenanceBox(
                name =  "Done",
                items = doneMaintenances,
                navController =  navController,
                viewModel = overviewScreenViewModel
            )
            MaintenanceBox(
                name =  "Cancelled",
                items = cancelledMaintenances,
                navController =  navController,
                viewModel = overviewScreenViewModel
            )
        }
    }
}

@Composable
fun MaintenanceBox(name: String, items: List<Maintenance>, navController: NavController, viewModel: ViewModel) {
    if (items.isNotEmpty()) {
        Text(text = name, modifier = Modifier.padding(start = 16.dp))
        Box {
            ListOfMaintenanceTask(
                modifier = Modifier,
                maintenance = items,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}