package com.example.maintainease.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
            childColumns = ["stuffId"],
            onDelete = ForeignKey.CASCADE
        )
    ]    ,
    primaryKeys = ["stuffId", "teamId"]
)
data class TeamStaffRelation(
    val stuffId: Int,
    val teamId: Int,
)
