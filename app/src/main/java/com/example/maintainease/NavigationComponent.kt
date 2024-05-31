package com.example.maintainease

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.maintainease.screen.DetailScreen
import com.example.maintainease.screen.NewTaskScreen
import com.example.maintainease.screen.OverviewScreen


sealed class NavigationHandling(val route: String) {
    data object OverviewScreen : NavigationHandling("overview")
    data object NewTaskScreen : NavigationHandling("newTask")
    data object Detail : NavigationHandling("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
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
        composable(NavigationHandling.Detail.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            Log.d("Navigation", "Navigating to DetailScreen with taskId: $taskId")
            if (taskId != null) {
                DetailScreen(taskId, navController)
            } else {
                // If the ID is not found, navigate back to the overview screen
                Text("ID not found")
                navController.navigate(NavigationHandling.OverviewScreen.route) {
                    popUpTo(NavigationHandling.OverviewScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}