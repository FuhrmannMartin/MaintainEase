package com.example.maintainease.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maintainease.data.InjectorUtils
import com.example.maintainease.data.Maintenance
import com.example.maintainease.viewModel.NewTaskScreenViewModel
import com.example.maintainease.widgets.SimpleBottomAppBar
import com.example.maintainease.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun NewTaskScreen(
    navController: NavController
)
{
    val context = LocalContext.current
    //val newTaskScreenViewModel: NewTaskScreenViewModel =
        val viewModel: NewTaskScreenViewModel = viewModel(factory = InjectorUtils.provideNewTaskScreenViewModelFactory(context))
   // val maintenances by newTaskScreenViewModel.maintenances.collectAsState()
    var textTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textDescription by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textLocation by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val scrollState = rememberScrollState()

    var dropDownexpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Select") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Add a new Maintenance Task")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(state = scrollState)) {

            OutlinedTextField(modifier = Modifier
                .padding(all = 15.dp)
                .height(60.dp)
                .fillMaxWidth(), value = textTitle,  onValueChange = { newText -> textTitle = newText}, label = { Text(text = "Title: ")} )
            OutlinedTextField(modifier = Modifier
                .padding(all = 15.dp)
                .height(200.dp)
                .fillMaxWidth(), value = textDescription,  onValueChange = { newText -> textDescription = newText}, label = { Text(text = "Description: ")} )

            OutlinedTextField(modifier = Modifier
                .padding(all = 15.dp)
                .height(100.dp)
                .fillMaxWidth(), value = textLocation,  onValueChange = { newTextLocation -> textLocation = newTextLocation}, label = { Text(text = "Location: ")} )
        //   Text(text = "PLACEHOLDER")
        Row(modifier = Modifier.padding(start = 15.dp)) {
            Text("Severity: ", modifier = Modifier.padding(top=15.dp, start = 20.dp))
            Box(modifier = Modifier.padding(15.dp, bottom = 0.dp)) {
                Button(
                    onClick = { dropDownexpanded = true },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(selectedItem)
                }
                DropdownMenu(
                    expanded = dropDownexpanded,
                    onDismissRequest = { dropDownexpanded = false }) {
                    DropdownMenuItem(text = { Text("Low") }, onClick = {
                        selectedItem = "Low"
                        dropDownexpanded = false
                    })
                    DropdownMenuItem(text = { Text("Middle") }, onClick = {
                        selectedItem = "Middle"
                        dropDownexpanded = false
                    })
                    DropdownMenuItem(text = { Text("High") }, onClick = {
                        selectedItem = "High"
                        dropDownexpanded = false
                    })
                }


            }
        }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)) {
                OutlinedButton(onClick = {
                    coroutineScope.launch {
                        val mainten = Maintenance(title = textTitle.text, description = textDescription.text, location = textLocation.text, severity = selectedItem, status = "open", teamId = 2, picture = null, date = null)
                        viewModel.addMaintenance(maintenance = mainten )
                    } }) {
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



fun saveTasktoDb( onAddClick: (Maintenance) -> Unit = {} ){
    {

    }
}


