package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDAO {
    @Insert
    suspend fun addMaintenance(maintenance: Maintenance) {
        insertMaintenanceInternal(maintenance)
        // @Query("INSERT INTO staffMaintenanceRelation (maintenanceId, maintenanceId) VALUES (0, 0)")
    }

    @Insert
    suspend fun insertMaintenanceInternal(maintenance: Maintenance)

    @Update
    suspend fun updateMaintenance(maintenances: List<Maintenance>)

    @Query("SELECT * FROM maintenance")
    fun getAllMaintenance(): Flow<List<Maintenance>>

    @Query("SELECT * FROM maintenance WHERE id = :taskId")
    fun getMaintenanceById(taskId: Int): Flow<Maintenance?>

    //@Query("SELECT staffMaintenanceRelation.staffId FROM maintenance INNER JOIN staffMaintenanceRelation ON maintenance.id = staffMaintenanceRelation.maintenanceId")
    //fun getAssignee(taskId: Int): Int?

}