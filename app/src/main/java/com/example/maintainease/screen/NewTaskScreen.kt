package com.example.maintainease.screen

import android.annotation.SuppressLint
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
import com.example.maintainease.data.entities.Maintenance
import com.example.maintainease.viewModel.NewTaskScreenViewModel
import com.example.maintainease.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun NewTaskScreen(
    navController: NavController
) {
    val currentDate = Date()

    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    val formattedDate: String = dateFormat.format(currentDate)

    val context = LocalContext.current
    //val newTaskScreenViewModel: NewTaskScreenViewModel =
    val viewModel: NewTaskScreenViewModel =
        viewModel(factory = InjectorUtils.provideNewTaskScreenViewModelFactory(context))
    // val maintenances by newTaskScreenViewModel.maintenances.collectAsState()
    var textTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isError by remember { mutableStateOf(false) }
    var isErrorTitle by remember { mutableStateOf(false) }
    var isErrorDescription by remember { mutableStateOf(false) }
    var isErrorLocation by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val errorMessageText = "Make sure to fill out all fields"

    var textDescription by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textLocation by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val scrollState = rememberScrollState()

    var dropDownExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("low") }
    val coroutineScope = rememberCoroutineScope()

    // Camera Stuff

    /* CheckPermissions(
        permission = Manifest.permission.CAMERA,
        onPermissionGranted = {
            showCamera = true
        },
        onPermissionDenied = {
            errorMessage = "Camera permission is required to use this feature."
        }
    ) */

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Add a new Maintenance Task",
                onLogoutClick = {
                    navController.navigate("login")
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(state = scrollState)
        ) {

            OutlinedTextField(modifier = Modifier
                .padding(all = 5.dp)
                .height(60.dp)
                .fillMaxWidth(),
                value = textTitle,
                onValueChange = { newText -> textTitle = newText },
                label = {
                    Text(text = "Title: ")
                    isErrorTitle = textTitle.text.isEmpty()
                    errorMessage = ""
                })

            IsErrorFun(isError = isErrorTitle, errorMessage = errorMessage)

            OutlinedTextField(modifier = Modifier
                .padding(all = 3.dp)
                .height(200.dp)
                .fillMaxWidth(),
                value = textDescription,
                onValueChange = { newText -> textDescription = newText },
                label = {
                    Text(text = "Description: ")
                    isErrorDescription = textDescription.text.isEmpty()
                    errorMessage = ""
                })

            IsErrorFun(isError = isErrorDescription, errorMessage = errorMessage)

            OutlinedTextField(modifier = Modifier
                .padding(all = 3.dp)
                .height(100.dp)
                .fillMaxWidth(),
                value = textLocation,
                onValueChange = { newTextLocation -> textLocation = newTextLocation },
                label = {
                    Text(text = "Location: ")
                    isErrorLocation = textLocation.text.isEmpty()
                    errorMessage = if (isErrorLocation) errorMessageText else ""
                })

            IsErrorFun(isError = isErrorLocation, errorMessage = errorMessage)
            Row(modifier = Modifier.padding(start = 3.dp)) {
                Text(
                    "Severity (Standard: Low): ",
                    modifier = Modifier.padding(top = 15.dp, start = 20.dp)
                )
                Box(modifier = Modifier.padding(15.dp, bottom = 0.dp)) {
                    Button(
                        onClick = { dropDownExpanded = true },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(selectedItem)
                    }
                    DropdownMenu(
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false }) {
                        DropdownMenuItem(text = { Text("Low") }, onClick = {
                            selectedItem = "low"
                            dropDownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Middle") }, onClick = {
                            selectedItem = "middle"
                            dropDownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("High") }, onClick = {
                            selectedItem = "high"
                            dropDownExpanded = false
                        })
                    }


                }
            }
            /* Row() {
                if (showCamera) {
                    CameraAdd(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        onImageCaptured = { uri ->
                            capturedImageUri = uri
                            filledImageUri = uri.toString()
                            showCamera = false
                        },
                        onError = { exception ->
                            Log.e("CameraX", "Camera error", exception)
                        }
                    )
                } else {
                    Button(
                        onClick = { showCamera = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Open Camera")
                    }
                    capturedImageUri?.let {
                        Image(
                            painter = rememberAsyncImagePainter(model = it),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }
                }
            } */

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                OutlinedButton(onClick = {
                    if (isErrorTitle || isErrorDescription || isErrorLocation) {
                        isError = true
                        errorMessage = errorMessageText
                    } else {
                        coroutineScope.launch {
                            val maintenance = Maintenance(
                                title = textTitle.text,
                                description = textDescription.text,
                                location = textLocation.text,
                                severity = selectedItem,
                                status = "open",
                                teamId = 0,
                                picture = null,
                                date = dateFormat.parse(formattedDate)
                            )
                            viewModel.addMaintenance(maintenance = maintenance)
                            navController.popBackStack()
                        }
                    }
                }) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text("Cancel")
                }
                IsErrorFun(isError = isError, errorMessage = errorMessage)
            }
        }
    }
}


@Composable
fun IsErrorFun(isError: Boolean, errorMessage: String) {
    if (isError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
    }
}