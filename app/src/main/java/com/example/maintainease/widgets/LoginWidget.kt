package com.example.maintainease.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.maintainease.data.entities.Staff
import com.example.maintainease.data.entities.Team
import com.example.maintainease.viewModel.LoginScreenViewModel

@Composable
fun LoginUI(viewModel: LoginScreenViewModel, onLoginSuccess: () -> Unit) {
    val staffList by viewModel.staffList.collectAsState()
    val teamList by viewModel.teamList.collectAsState()

    var selectedStaff by remember { mutableStateOf<Staff?>(null) }
    var selectedTeam by remember { mutableStateOf<Team?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Select Staff")
        staffList.forEach { staff ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedStaff == staff),
                        onClick = { selectedStaff = staff },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (selectedStaff == staff),
                    onClick = { selectedStaff = staff }
                )
                Text(text = staff.name, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Text(text = "Select Team")
        teamList.forEach { team ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedTeam == team),
                        onClick = { selectedTeam = team },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (selectedTeam == team),
                    onClick = { selectedTeam = team }
                )
                Text(text = team.title, modifier = Modifier.padding(start = 8.dp))
            }
        }

        OutlinedButton(
            onClick = {
                selectedStaff?.let { staff ->
                    selectedTeam?.let { team ->
                        viewModel.setCurrentUser(staff.id, team.id)
                        onLoginSuccess()
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login")
        }
    }
}