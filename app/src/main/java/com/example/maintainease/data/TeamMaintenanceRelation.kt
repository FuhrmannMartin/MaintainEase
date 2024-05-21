package com.example.maintainease.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "teamMaintenanceRelation",
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Maintenance::class,
            parentColumns = ["id"],
            childColumns = ["maintenanceId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["maintenanceId"], unique = true)]
)
class TeamMaintenanceRelation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val maintenanceId: Int,
    val teamId: Int?,
)
