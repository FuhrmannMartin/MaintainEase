package com.example.maintainease.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.maintainease.R
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
    val description: String,
    val picture: Int?
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
            picture = R.drawable.lampe_task
        ),
        Maintenance(
            title = "Gelände lose",
            location = "Streppenhaus von Gebäude A, 1. Stock",
            date = dateFormat.parse("13.05.2024"),
            severity = "high",
            status = "open",
            description = "Das Gelände ist lose, gehen darauf ist sehr gefährlich",
            picture = R.drawable.treppenhaus_task
        ),
        Maintenance(
            title = "Wand verschmutzt",
            location = "1. Stock Raum B.022",
            date = dateFormat.parse("14.05.2024"),
            severity = "low",
            status = "open",
            description = "Wand ist schmutzig, muss neu angestrichen werden.",
            picture = R.drawable.wand_task
        ),
        Maintenance(
            title = "Toilette veropft",
            location = "4. Stock Raum C.01",
            date = dateFormat.parse("14.05.2024"),
            severity = "middle",
            status = "open",
            description = "Bitte entsopfen",
            picture = R.drawable.wc_task
        ),
        Maintenance(
            title = "Laser kaputt",
            location = "5. Stock Raum D.104",
            date = dateFormat.parse("10.05.2024"),
            severity = "middle",
            status = "open",
            description = "Laser im Labor ist kaputt, muss ausgetauscht werden",
            picture = R.drawable.laser_task
        )
    )
}