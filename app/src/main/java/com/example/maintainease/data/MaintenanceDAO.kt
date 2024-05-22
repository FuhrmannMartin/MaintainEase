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

    @Query("SELECT * FROM maintenance WHERE teamId = :teamId")
    fun getAllMaintenance(teamId: Int): Flow<List<Maintenance>>

    @Query("SELECT * FROM maintenance WHERE id = :taskId")
    fun getMaintenanceById(taskId: Int): Flow<Maintenance?>

    @Query("SELECT staffId FROM staffMaintenanceRelation WHERE maintenanceId = :taskId")
    fun getAssigneeIdForTask(taskId: Int): Flow<Int?>

    @Query("SELECT * FROM staff WHERE id = :staffId")
    fun getAssigneeById(staffId: Int): Flow<Staff?>

    @Query("Update staffMaintenanceRelation set staffId = :staffId WHERE maintenanceId = :taskId")
    suspend fun mapTaskToStaff(taskId: Int, staffId: Int)

}