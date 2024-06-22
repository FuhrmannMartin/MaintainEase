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

//  Gardening = TeamId 1, Electrician = TeamId 2
// Options for status: open, working, done, cancel
// Options for severity: high, middle, low
fun getMaintenance(): List<Maintenance> {
    return listOf(
        Maintenance(
            title = "Mowing the lawn",
            location = "Rose-Garden",
            date = dateFormat.parse("18.06.2024"),
            severity = "low",
            status = "open",
            description = "Please mow the law at the Rose-Garden",
            picture = R.drawable.rose_garden,
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Plant flowers",
            location = "C.4.50 Balcony",
            date = dateFormat.parse("18.06.2024"),
            severity = "high",
            status = "working",
            description = "The customer complained, that the flowers in Room C.4.50 are dead. Please Replace Flowers",
            picture = R.drawable.c_4_50_balcony,
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Pruning shrubs",
            location = "Labyrinth-Garden",
            date = dateFormat.parse("18.06.2024"),
            severity = "low",
            status = "cancelled",
            description = "The shrubs need regular maintenance ",
            picture = R.drawable.labyrinth_garden,
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Water entry plants",
            location = "E.0.0",
            date = dateFormat.parse("13.05.2024"),
            severity = "middle",
            status = "done",
            description = "Watering for the Plants in the Entry needed",
            picture = R.drawable.e_0_0,
            teamId = 1,
        ),
        Maintenance(
            title = "Work on broken elevator",
            location = "El.1",
            date = dateFormat.parse("10.06.2024"),
            severity = "high",
            status = "open",
            description = "The Nr. 1 Elevator is broken and is very important to transport our guests. Highest priority",
            picture = R.drawable.el_1,
            teamId = 2,
            comments = emptyList()
        ),
        Maintenance(
            title = "Change Light-Bulbs",
            location = "A.3.40",
            date = dateFormat.parse("18.06.2024"),
            severity = "middle",
            status = "done",
            description = "In a Guest Room in Truck A the reading lamp is not working.",
            picture = R.drawable.a_3_40,
            teamId = 2,
            comments = emptyList()
        ),
        Maintenance(
            title = "Replace Cabling",
            location = "M.4.10",
            date = dateFormat.parse("18.06.2024"),
            severity = "low",
            status = "working",
            description = "The cables in the Meeting Room are already pretty old and often have loose contact",
            picture = R.drawable.m_4_10,
            teamId = 2,
            comments = emptyList()
        ),
        Maintenance(
            title = "Troubleshoot Phone",
            location = "B.2.14",
            date = dateFormat.parse("13.05.2024"),
            severity = "high",
            status = "cancelled",
            description = "The Phone in the Guest Room can't call the reception but other numbers. Reason unknown.",
            picture = R.drawable.b_2_14,
            teamId = 2,
        )
    )

}