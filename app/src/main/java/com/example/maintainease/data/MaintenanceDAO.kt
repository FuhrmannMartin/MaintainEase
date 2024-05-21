package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDAO {
    @Insert
    suspend fun insertMaintenance(maintenance: Maintenance): Long

    @Query("INSERT INTO staffMaintenanceRelation (staffId, maintenanceId) VALUES (Null, :taskId)")
    suspend fun insertStaffMaintenanceRelation(taskId: Long)

    @Update
    suspend fun updateMaintenance(maintenances: List<Maintenance>)

    @Query("SELECT * FROM maintenance")
    fun getAllMaintenance(): Flow<List<Maintenance>>

    @Query("SELECT * FROM maintenance WHERE id = :taskId")
    fun getMaintenanceById(taskId: Int): Flow<Maintenance?>

    @Query("SELECT staffId FROM staffMaintenanceRelation WHERE maintenanceId = :taskId")
    fun getAssigneeId(taskId: Int): Flow<Int?>

}