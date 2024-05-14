package com.example.maintainease.widgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.maintainease.data.Maintenance


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 125.dp),
        title = { Text(text = title) })
}

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun SimpleBottomAppBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavigationItem(
            title = "Overview",
            icon = Icons.Filled.List
        ),
        BottomNavigationItem(
            title = "New Task",
            icon = Icons.Filled.Add
        )
    )

    NavigationBar {
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = currentRoute == item.title,
                onClick = {
                    navController.navigate(item.title) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(item.title) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .padding(8.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (currentRoute == item.title) Color.White else Color.Black
                            )
                            Text(
                                text = item.title,
                                color = if (currentRoute == item.title) Color.White else Color.Black,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun MaintenanceCardHeader(
    maintenance: Maintenance,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {

    }
}

@Composable
fun MaintenanceDetails(
    modifier: Modifier,
    maintenance: Maintenance) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = maintenance.title)
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
        Column(modifier = modifier) {
            Text(text = "Location: ${maintenance.location}")
            Text(text = "Date: ${maintenance.date}")
            Text(text = "Severity: ${maintenance.severity}")
            Text(text = "Status: ${maintenance.status}")
            Text(text = "Description: ${maintenance.description}")

            Divider(modifier = Modifier.padding(3.dp))
        }
    }
}

@Composable
fun MaintenanceTask(
    modifier: Modifier,
    maintenance: Maintenance,
    onItemClick: (Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onItemClick(maintenance.id)
            }, shape = ShapeDefaults.Large, elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            MaintenanceCardHeader(modifier = modifier, maintenance = maintenance)
            MaintenanceDetails(modifier = modifier.padding(12.dp), maintenance = maintenance)
        }
    }
}

@Composable
fun ListOfMaintenanceTask(
    modifier: Modifier = Modifier,
    maintenance: List<Maintenance>,
    navController: NavController,
    viewModel: ViewModel
) {
    Log.d("ListOfVisibleObjectGroups", "Displaying ${maintenance.size} movies")

    LazyColumn(modifier = modifier) {
        try {
            items(maintenance) { maintenanceItem ->
                MaintenanceTask(modifier = modifier, maintenance = maintenanceItem)
            }
        } catch (e: Exception) {
            Log.e("ListOfVisibleObjectGroups", "Error displaying maintenance Task: ${e.message}")
        }
    }
}




