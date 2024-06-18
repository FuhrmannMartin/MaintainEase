package com.example.maintainease.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.data.entities.MaintenanceWithAssignee
import com.example.maintainease.data.entities.getCurrentUser
import com.example.maintainease.data.entities.getTeam
import com.example.maintainease.viewModel.OverviewScreenViewModel
import com.example.maintainease.widgets.ListOfMaintenanceTask
import com.example.maintainease.widgets.MyCheckbox
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

    // A single mutableStateOf for managing the checkboxes' states
    var checkedState by remember { mutableStateOf(CheckedState.None) }
    var filteredOpenMaintenances = openMaintenances
    var filteredInProgressMaintenances = inProgressMaintenances
    var filteredDoneMaintenances = doneMaintenances
    var filteredCancelledMaintenances = cancelledMaintenances

    // Update filtered lists based on the checkedState
    when (checkedState) {
        CheckedState.MyTasks -> {
            filteredOpenMaintenances =
                openMaintenances.filter { it.assignee?.id == overviewScreenViewModel.currentUser["staffId"] }
            filteredInProgressMaintenances =
                inProgressMaintenances.filter { it.assignee?.id == overviewScreenViewModel.currentUser["staffId"] }
            filteredDoneMaintenances =
                doneMaintenances.filter { it.assignee?.id == overviewScreenViewModel.currentUser["staffId"] }
            filteredCancelledMaintenances =
                cancelledMaintenances.filter { it.assignee?.id == overviewScreenViewModel.currentUser["staffId"] }
        }
        CheckedState.UnassignedTasks -> {
            filteredOpenMaintenances =
                openMaintenances.filter { it.assignee?.id == null }
            filteredInProgressMaintenances =
                inProgressMaintenances.filter { it.assignee?.id == null }
            filteredDoneMaintenances =
                doneMaintenances.filter { it.assignee?.id == null }
            filteredCancelledMaintenances =
                cancelledMaintenances.filter { it.assignee?.id == null }
        }
        else -> {
            // Both checkboxes are unchecked, no filtering needed
        }
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "MaintainEase")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        },
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            item {
                val teamTitle = getLoggedInTeamTitle()
                Text(
                    text = "Team $teamTitle",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            item {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    MyCheckbox(
                        isChecked = checkedState == CheckedState.MyTasks,
                        onCheckedChange = {
                            checkedState = if (it) CheckedState.MyTasks else CheckedState.None
                        },
                        text = "My Tasks"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    MyCheckbox(
                        isChecked = checkedState == CheckedState.UnassignedTasks,
                        onCheckedChange = {
                            checkedState = if (it) CheckedState.UnassignedTasks else CheckedState.None
                        },
                        text = "Unassigned"
                    )
                }
            }
            items(listOf(
                "Open" to filteredOpenMaintenances,
                "In Progress" to filteredInProgressMaintenances,
                "Done" to filteredDoneMaintenances,
                "Cancelled" to filteredCancelledMaintenances
            )) { (name, items) ->
                MaintenanceBox(
                    name = name,
                    items = items,
                    navController = navController,
                    viewModel = overviewScreenViewModel
                )
            }
        }
    }
}

enum class CheckedState {
    None,
    MyTasks,
    UnassignedTasks
}

@Composable
fun MaintenanceBox(name: String, items: List<MaintenanceWithAssignee>, navController: NavController, viewModel: ViewModel) {
    if (items.isNotEmpty()) {
        Text(text = name, modifier = Modifier.padding(start = 16.dp))
        Box {
            ListOfMaintenanceTask(
                modifier = Modifier,
                maintenanceWithAssignee = items,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

private fun getLoggedInTeamTitle(): String {
    val currentUser = getCurrentUser()
    val teamId = currentUser["teamId"] ?: return "Unknown"

    val teams = getTeam()
    val team = teams.find { it.id == teamId }
    return team?.title ?: "Unknown"
}