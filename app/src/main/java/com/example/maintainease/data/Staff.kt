package com.example.maintainease.data

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

fun getCurrentUser(): String {
    return "Martin"
}