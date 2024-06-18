package com.example.maintainease.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.maintainease.data.entities.getTeam
import com.example.maintainease.screen.UserSelection

@Composable
fun UserSelectionScreen(userSelection: UserSelection, onSelectionChanged: (Int, Int) -> Unit) {
    Column {
        Text("Select Staff ID")
        Row {
            for (i in 1..3) {
                Checkbox(
                    checked = userSelection.staffId == i,
                    onCheckedChange = { if (it) onSelectionChanged(i, userSelection.teamId) }
                )
                Text("Staff $i")
            }
        }

        Text("Select Team ID")
        Row {
            for (i in 1..3) {
                Checkbox(
                    checked = userSelection.teamId == i,
                    onCheckedChange = { if (it) onSelectionChanged(userSelection.staffId, i) }
                )
                Text("Team $i")
            }
        }
    }
}


@Composable
fun StaffCheckbox(staffId: Int, selectedStaffId: Int, onCheckedChange: (Int) -> Unit) {
    Row {
        Checkbox(
            checked = (staffId == selectedStaffId),
            onCheckedChange = { if (it) onCheckedChange(staffId) }
        )
        Text("Staff ID $staffId")
    }
}

@Composable
fun TeamCheckbox(teamId: Int, selectedTeamId: Int, onCheckedChange: (Int) -> Unit) {
    Row {
        Checkbox(
            checked = (teamId == selectedTeamId),
            onCheckedChange = { if (it) onCheckedChange(teamId) }
        )
        Text("Team ID $teamId")
    }
}

fun getLoggedInTeamTitle(selection: UserSelection): String {
    val teams = getTeam()
    val team = teams.find { it.id == selection.teamId }
    return team?.title ?: "Unknown"
}