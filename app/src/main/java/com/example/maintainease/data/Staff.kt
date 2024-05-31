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

object globalCurrentUser {
    lateinit var globalCurrentUserVariable: Map<String,Int>
}

fun getCurrentUser(): Map<String, Int> {
        return globalCurrentUser.globalCurrentUserVariable
}

fun setCurrentUser(staffID: Int, teamID: Int){
    globalCurrentUser.globalCurrentUserVariable = mapOf("staffId" to staffID, "teamId" to teamID)
}