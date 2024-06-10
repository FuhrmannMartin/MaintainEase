package com.example.maintainease.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.maintainease.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "maintenance")
class Maintenance(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val location: String,
    val date: Date?,
    val severity: String,
    var status: String,
    val description: String,
    val picture: Int?,
    var teamId: Int,
    var comments: List<String> = emptyList()
)

class MaintenanceWithAssignee(
    val maintenance: Maintenance,
    val assignee: Staff? = null
)

val locale = Locale("German", "Austria")
val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)

fun getMaintenance(): List<Maintenance> {
    return listOf(
        Maintenance(
            title = "Mowing the lawn",
            location = "",
            date = dateFormat.parse(""),
            severity = "",
            status = "cancel",
            description = "",
            picture = 0,
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Work on broken elevator",
            location = "El.1",
            date = dateFormat.parse("10.06.2024"),
            severity = "high",
            status = "done",
            description = "The Nr. 1 Elevator is broken and is very important to transport our guests. Highest priority",
            picture = 0,
            teamId = 2,
            comments = emptyList()
        ),
        Maintenance(
            title = "Plant flowers",
            location = "",
            date = dateFormat.parse(""),
            severity = "",
            status = "done",
            description = "",
            picture = 0,
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Change Light-Bulbs",
            location = "A.3.40",
            date = dateFormat.parse(""),
            severity = "middle",
            status = "cancel",
            description = "In a Guest Room in Truck A the reading lamp is not working.",
            picture = 0,
            teamId = 2,
            comments = emptyList()
        ),
        Maintenance(
            title = "Pruning shrubs",
            location = "",
            date = dateFormat.parse(""),
            severity = "",
            status = "working",
            description = "",
            picture = 0,
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Replace Cabling",
            location = "M.4.10",
            date = dateFormat.parse(""),
            severity = "low",
            status = "working",
            description = "The cables in the Meeting Room are already pretty old and often have loose contact",
            picture = 0,
            teamId = 2,
            comments = emptyList()
        ),
        Maintenance(
            title = "Water entry plans",
            location = "Streppenhaus von Gebäude A, 1. Stock",
            date = dateFormat.parse("13.05.2024"),
            severity = "high",
            status = "open",
            description = "Das Gelände ist lose, gehen darauf ist sehr gefährlich",
            picture = R.drawable.treppenhaus_task,
            teamId = 1,
        ),
        Maintenance(
            title = "Troubleshoot Phone",
            location = "B.2.14",
            date = dateFormat.parse("13.05.2024"),
            severity = "high",
            status = "open",
            description = "The Phone in the Guest Room can't call the reception but other numbers. Reason unknown.",
            picture = R.drawable.treppenhaus_task,
            teamId = 2,
        )
    )

}