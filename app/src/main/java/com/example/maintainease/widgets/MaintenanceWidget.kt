package com.example.maintainease.widgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.maintainease.NavigationHandling
import com.example.maintainease.data.entities.MaintenanceWithAssignee
import com.example.maintainease.data.entities.dateFormat

@Composable
fun ListOfMaintenanceTask(
    modifier: Modifier,
    maintenanceWithAssignee: List<MaintenanceWithAssignee>,
    navController: NavController,
    viewModel: ViewModel
) {
    Log.d("ListOfVisibleObjectGroups", "Displaying ${maintenanceWithAssignee.size} movies")
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.padding(bottom = 1.dp, start = 6.dp)) {
            for (maintenanceItem in maintenanceWithAssignee) {
                MaintenanceTask(maintenanceWithAssignee = maintenanceItem,
                        navController = navController,
                        onItemClick = { id ->
                            navController.navigate(NavigationHandling.Detail.createRoute(id))
                        })
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun MaintenanceTask(
    maintenanceWithAssignee: MaintenanceWithAssignee,
    navController: NavController,
    onItemClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable {
                onItemClick(maintenanceWithAssignee.maintenance.id)
            },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination

            if (currentDestination?.route?.startsWith("detail/") == true) {
                MaintenanceCardHeader(maintenanceWithAssignee = maintenanceWithAssignee)
                MaintenanceDetails(maintenanceWithAssignee = maintenanceWithAssignee, showDetailsInit = true)
            } else {
                MaintenanceDetails(
                    maintenanceWithAssignee = maintenanceWithAssignee,
                    showDetailsInit = false
                )
            }
        }
    }
}

@Composable
fun MaintenanceDetails(
    maintenanceWithAssignee: MaintenanceWithAssignee,
    showDetailsInit: Boolean
) {
    var showDetails by remember {
        mutableStateOf(showDetailsInit)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp, start = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = maintenanceWithAssignee.maintenance.title)
        Icon(
            modifier = Modifier.clickable {
                showDetails = !showDetails
            }, imageVector = if (showDetails) Icons.Filled.KeyboardArrowDown
            else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
        )
    }

    AnimatedVisibility(
        visible = showDetails, enter = fadeIn(), exit = fadeOut()
    ) {
        Column {
            Text(text = "Location: ${maintenanceWithAssignee.maintenance.location}")
            Text(
                text = "Date: ${
                    maintenanceWithAssignee.maintenance.date?.let {
                        dateFormat.format(
                            it
                        )
                    }
                }"
            )
            Text(text = "Assignee: ${maintenanceWithAssignee.assignee?.name}")
            Text(text = "Severity: ${maintenanceWithAssignee.maintenance.severity}")
            Text(text = "Status: ${maintenanceWithAssignee.maintenance.status}")
            Text(text = "Description: ${maintenanceWithAssignee.maintenance.description}")
        }
    }
}

@Composable
fun MaintenanceCardHeader(maintenanceWithAssignee: MaintenanceWithAssignee) {
    /*
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        maintenanceWithAssignee.maintenance.picture?.let { picture ->
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = picture),
                contentDescription = "Picture of Task",
                contentScale = ContentScale.Crop
            )
        }
    }
} */
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        maintenanceWithAssignee.maintenance.picture?.let { picture ->
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = rememberAsyncImagePainter(model = picture),
                contentDescription = "Picture of Task",
                contentScale = ContentScale.Crop
            )
        }
    }
}