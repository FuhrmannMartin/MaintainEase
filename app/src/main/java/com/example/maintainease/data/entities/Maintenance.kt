package com.example.maintainease.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val picture: String?,
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
            title = "Lampe Kaputt",
            location = "2. Stock Raum A.101",
            date = dateFormat.parse("12.05.2024"),
            severity = "low",
            status = "open",
            description = "Die Lampe muss ausgetauscht werden E27",
            picture = "android.resource://com.example.maintainease/drawable/lampe_task",
            teamId = 1,
            comments = emptyList()
        ),
        Maintenance(
            title = "Gelände lose",
            location = "Streppenhaus von Gebäude A, 1. Stock",
            date = dateFormat.parse("13.05.2024"),
            severity = "high",
            status = "open",
            description = "Das Gelände ist lose, gehen darauf ist sehr gefährlich",
            picture = "android.resource://com.example.maintainease/drawable/lampe_task",
            teamId = 1,
        ),
        Maintenance(
            title = "Wand verschmutzt",
            location = "1. Stock Raum B.022",
            date = dateFormat.parse("14.05.2024"),
            severity = "low",
            status = "open",
            description = "Wand ist schmutzig, muss neu angestrichen werden.",
            picture = "android.resource://com.example.maintainease/drawable/lampe_task",
            teamId = 1,
        ),
        Maintenance(
            title = "Toilette verstopft",
            location = "4. Stock Raum C.01",
            date = dateFormat.parse("14.05.2024"),
            severity = "middle",
            status = "in progress",
            description = "Bitte entstopfen",
            picture = "android.resource://com.example.maintainease/drawable/lampe_task",
            teamId = 1,
        ),
        Maintenance(
            title = "Test",
            location = "4. Stock Raum C.02",
            date = dateFormat.parse("14.05.2025"),
            severity = "low",
            status = "in progress",
            description = "Bitte entstopfen",
            picture = "android.resource://com.example.maintainease/drawable/lampe_task",
            teamId = 1,
        ),
        Maintenance(
            title = "Laser kaputt",
            location = "5. Stock Raum D.104",
            date = dateFormat.parse("10.05.2024"),
            severity = "middle",
            status = "done",
            description = "Laser im Labor ist kaputt, muss ausgetauscht werden",
            picture = "android.resource://com.example.maintainease/drawable/lampe_task",
            teamId = 1,
        )
    )

}