package com.example.maintainease.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.maintainease.NavigationHandling
import com.example.maintainease.data.getCurrentUser
import com.example.maintainease.data.getStaff
import com.example.maintainease.data.setCurrentUser


@Composable
fun LoginScreen(navController: NavController) {
    var dropDownexpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Please Select an User") }

    var staff = getStaff()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "This is MaintenanceEase",
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Box(modifier = Modifier.padding(15.dp, bottom = 0.dp)) {
            Button(
                onClick = { dropDownexpanded = true },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(selectedItem)
            }
            DropdownMenu(
                expanded = dropDownexpanded,
                onDismissRequest = { dropDownexpanded = false }
            ) {
                staff.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label.name) },
                        onClick = {
                            selectedItem = label.name
                            dropDownexpanded = false
                        })
                }
            }
        }
        
        if(selectedItem != "Please select your User"){
            var userMap: Int 
            if(selectedItem != "Martin")
            {
                userMap = 1
            }
            else {
                userMap = 2
            }
            setCurrentUser(userMap, userMap)
            Button(onClick = { navController.navigate(NavigationHandling.OverviewScreen.route) }) {
                Text(text = "Select")
            }
        }
    }
}






/*
                DropdownMenu(
                    expanded = dropDownexpanded,
                    onDismissRequest = { dropDownexpanded = false }) {
                    DropdownMenuItem(text = { Text("Martin") }, onClick = {
                        selectedItem = "1"
                        dropDownexpanded = false
                    })
                    DropdownMenuItem(text = { Text("Nurit") }, onClick = {
                        selectedItem = "2"
                        dropDownexpanded = false
                    })
                    DropdownMenuItem(text = { Text("Roman") }, onClick = {
                        selectedItem = "2"
                        dropDownexpanded = false
                    })

                }

        }
        //DropdownMenuExample()
    }
*/
