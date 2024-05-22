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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.maintainease.NavigationHandling
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.dateFormat

@Composable
fun ListOfMaintenanceTask(
    modifier: Modifier,
    maintenance: List<Maintenance>,
    navController: NavController,
    viewModel: ViewModel
) {
    Log.d("ListOfVisibleObjectGroups", "Displaying ${maintenance.size} movies")
    Column{
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.padding(bottom = 1.dp, start = 6.dp)) {
            try {
                items(maintenance) { maintenanceItem ->
                    MaintenanceTask(maintenance = maintenanceItem, navController = navController)
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            } catch (e: Exception) {
                Log.e("ListOfVisibleObjectGroups", "Error displaying maintenance Task: ${e.message}")
            }
        }
    }
}

@Composable
fun MaintenanceTask(
    maintenance: Maintenance,
    navController: NavController,
    onItemClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable {
                navController.navigate(NavigationHandling.Detail.createRoute(maintenance.id))
            },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination

            if (currentDestination?.route?.startsWith("detail/") == true) {
                MaintenanceCardHeader(maintenance = maintenance)
            }

            MaintenanceDetails(maintenance = maintenance)
        }
    }
}

@Composable
fun MaintenanceDetails(
    maintenance: Maintenance) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp, start = 6.dp),
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
        Column {
            Text(text = "Location: ${maintenance.location}")
            Text(text = "Date: ${maintenance.date?.let { dateFormat.format(it) }}")
            Text(text = "Severity: ${maintenance.severity}")
            Text(text = "Status: ${maintenance.status}")
            Text(text = "Description: ${maintenance.description}")
        }
    }
}

@Composable
fun MaintenanceCardHeader(maintenance: Maintenance) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        maintenance.picture?.let { picture ->
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = picture),
                contentDescription = "Picture of Task",
                contentScale = ContentScale.Crop
            )
        }
    }
}