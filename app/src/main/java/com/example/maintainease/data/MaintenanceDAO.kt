package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MaintenanceDAO {
    @Insert
    suspend fun addMaintenance(maintenance: Maintenance)

    @Update
    suspend fun updateMaintenance(maintenances: List<Maintenance>)

    @Query("SELECT COUNT(*) FROM maintenance WHERE title = :title")
    suspend fun maintenanceExists(title: String): Int

    @Transaction
    @Query("SELECT * FROM maintenance")
    fun getAllMaintenance(): List<Maintenance>
}