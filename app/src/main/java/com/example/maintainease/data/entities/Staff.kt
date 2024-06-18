package com.example.maintainease.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
class Staff(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)

fun getStaff(): List<Staff> {
    return listOf(
        Staff(
            name = "Nurit"
        ),
        Staff(
            name = "Roman"
        ),
        Staff(
            name = "Martin"
        )
    )
}

fun getCurrentUser(staffId: Int, teamId: Int): Map<String, Int> {
    return mapOf("staffId" to staffId, "teamId" to teamId)
}