package com.example.maintainease.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "teamStaffRelation",
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Staff::class,
            parentColumns = ["id"],
            childColumns = ["staffId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["staffId", "teamId"]
)
class TeamStaffRelation(
    val staffId: Int,
    val teamId: Int,
)
