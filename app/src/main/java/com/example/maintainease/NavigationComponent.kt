package com.example.maintainease

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.maintainease.screen.NewTaskScreen
import com.example.maintainease.screen.OverviewScreen


sealed class NavigationHandling(val route: String) {
    object OverviewScreen : NavigationHandling("overview")
    object NewTaskScreen: NavigationHandling("newTask")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationHandling.OverviewScreen.route) {
        composable(NavigationHandling.OverviewScreen.route) {
            OverviewScreen(navController)
        }
        composable(NavigationHandling.NewTaskScreen.route) {
            NewTaskScreen(navController)
        }


    }
}