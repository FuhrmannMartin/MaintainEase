package com.example.maintainease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.viewModel.LoginScreenViewModel
import com.example.maintainease.widgets.LoginUI
import com.example.maintainease.widgets.SimpleTopAppBarLogin

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: LoginScreenViewModel =
        viewModel(factory = InjectorUtils.provideLoginScreenViewModelFactory(context))

    Scaffold(
        topBar = {
            SimpleTopAppBarLogin(title = "Login")
        },
    ) { innerPadding ->
        Column( modifier = Modifier
            .padding(innerPadding)){
            LoginUI(viewModel = viewModel) {
                navController.navigate("overview")
            }
        }
    }
}