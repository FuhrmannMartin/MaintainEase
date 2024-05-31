package com.example.maintainease.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
)

fun getTeam(): List<Team> {
    return listOf(
        Team(
            title = "Fruehschicht"
        ),
        Team(
            title = "Spaetschicht"
        )
    )
}