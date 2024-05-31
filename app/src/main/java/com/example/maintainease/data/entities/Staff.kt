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

fun getCurrentUser(): Map<String, Int> {
    val currentUser = mapOf("staffId" to 1, "teamId" to 1)
    return currentUser
}