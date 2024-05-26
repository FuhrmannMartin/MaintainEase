package com.example.maintainease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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
            SimpleTopAppBar(title = "Add a new Maintenance Task")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            var text by remember {
                mutableStateOf(TextFieldValue(""))
            }
            var textLocation by remember {
                mutableStateOf(TextFieldValue(""))
            }

            OutlinedTextField(modifier = Modifier.padding(all = 15.dp).height(200.dp).fillMaxWidth(), value = text,  onValueChange = { newText -> text = newText}, label = { Text(text = "Description: ")} )

            OutlinedTextField(modifier = Modifier.padding(all = 15.dp).height(100.dp).fillMaxWidth(), value = textLocation,  onValueChange = { newTextLocation -> textLocation = newTextLocation}, label = { Text(text = "Location: ")} )
        //   Text(text = "PLACEHOLDER")

            Row(modifier = Modifier.fillMaxWidth().padding(30.dp)) {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text("Cancel")
                }
            }
        }
    }
}