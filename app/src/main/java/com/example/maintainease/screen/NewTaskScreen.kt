package com.example.maintainease.screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NewTaskScreen(
    navController: NavController
)
{
    var currentDate = Date()

    val dateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
    val formattedDate: String = dateFormat.format(currentDate)

    val context = LocalContext.current
    //val newTaskScreenViewModel: NewTaskScreenViewModel =
        val viewModel: NewTaskScreenViewModel = viewModel(factory = InjectorUtils.provideNewTaskScreenViewModelFactory(context))
   // val maintenances by newTaskScreenViewModel.maintenances.collectAsState()
    var textTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isError by remember { mutableStateOf(false) }
    var isErrorTitle by remember { mutableStateOf(false) }
    var isErrorDescription by remember { mutableStateOf(false) }
    var isErrorLocation by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val errorMessageText = "Cannot save. Please fill out every Field"

    var textDescription by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textLocation by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val scrollState = rememberScrollState()

    var dropDownexpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("low") }
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
                .fillMaxWidth(), value = textTitle,  onValueChange = { newText -> textTitle = newText}, label = { Text(text = "Title: ");
                isErrorTitle = textTitle.text.isEmpty()
                errorMessage = if (isErrorTitle) errorMessageText else ""} )

            isErrorFun(isError = isErrorTitle, errorMessage =errorMessage )

            OutlinedTextField(modifier = Modifier
                .padding(all = 15.dp)
                .height(200.dp)
                .fillMaxWidth(), value = textDescription,  onValueChange = { newText -> textDescription = newText}, label = { Text(text = "Description: ")
                isErrorDescription = textDescription.text.isEmpty()
                errorMessage = if (isErrorDescription) errorMessageText else ""} )

            isErrorFun(isError = isErrorDescription, errorMessage =errorMessage )

            OutlinedTextField(modifier = Modifier
                .padding(all = 15.dp)
                .height(100.dp)
                .fillMaxWidth(), value = textLocation,  onValueChange = { newTextLocation -> textLocation = newTextLocation}, label = { Text(text = "Location: ")
                isErrorLocation = textLocation.text.isEmpty()
                errorMessage = if (isErrorLocation) errorMessageText else ""} )

            isErrorFun(isError = isErrorLocation, errorMessage =errorMessage )
        //   Text(text = "PLACEHOLDER")
        Row(modifier = Modifier.padding(start = 15.dp)) {
            Text("Severity (Standard: Low): ", modifier = Modifier.padding(top=15.dp, start = 20.dp))
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
                        selectedItem = "low"
                        dropDownexpanded = false
                    })
                    DropdownMenuItem(text = { Text("Middle") }, onClick = {
                        selectedItem = "middle"
                        dropDownexpanded = false
                    })
                    DropdownMenuItem(text = { Text("High") }, onClick = {
                        selectedItem = "high"
                        dropDownexpanded = false
                    })
                }


            }
        }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)) {
                OutlinedButton(onClick = {
                    if(isErrorTitle || isErrorDescription || isErrorLocation){
                        isError = true
                        errorMessage = errorMessageText
                    }else{
                    coroutineScope.launch {
                        val mainten = Maintenance(
                            title = textTitle.text,
                            description = textDescription.text,
                            location = textLocation.text,
                            severity = selectedItem,
                            status = "open",
                            teamId = 0,
                            picture = null,
                            date = dateFormat.parse(formattedDate)
                        )
                        viewModel.addMaintenance(maintenance = mainten)
                        navController.popBackStack()
                    }
                    } }) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text("Cancel")
                }
                isErrorFun(isError = isError, errorMessage = errorMessage)
            }
        }
    }
}


@Composable
fun isErrorFun(isError: Boolean, errorMessage: String) {
    if (isError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
    }
}