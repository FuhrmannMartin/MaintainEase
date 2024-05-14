package com.example.maintainease.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "maintenance")
data class Maintenance(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val location: String,
    val date: Date?,
    val severity: String,
    val status: String,
    val description: String

)
val locale = Locale("German", "Austria")
val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)

fun getMaintenance(): List<Maintenance> {
    return listOf(
        Maintenance(
            title = "Job 1",
            location = "Beispieladresse 234",
            date = dateFormat.parse("12.05.2024"),
            severity = "high",
            status = "open",
            description = "Broken Window"
        ),
        Maintenance(
            title = "Job 2",
            location = "Beispieladresse 123",
            date = dateFormat.parse("13.05.2024"),
            severity = "middle",
            status = "closed",
            description = "Broken Window"
        )
    )
}