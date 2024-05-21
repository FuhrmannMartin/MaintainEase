package com.example.maintainease.repositories

import androidx.room.Transaction
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.MaintenanceDAO
import kotlinx.coroutines.flow.Flow

class MaintenanceRepository(private val maintenanceDAO: MaintenanceDAO) {

    @Transaction
    suspend fun addMaintenanceWithRelation(maintenance: Maintenance) {
        val maintenanceId = maintenanceDAO.insertMaintenance(maintenance)
        maintenanceDAO.insertStaffMaintenanceRelation(maintenanceId)
    }

    fun getAllMaintenance(): Flow<List<Maintenance>> {
        return maintenanceDAO.getAllMaintenance()
    }

    fun getMaintenanceById(taskId: Int): Flow<Maintenance?> {
        return maintenanceDAO.getMaintenanceById(taskId)
    }

    fun getAssigneeId(taskId: Int): Flow<Int?> {
        return maintenanceDAO.getAssigneeId(taskId)
    }

    companion object {
        @Volatile
        private var instance: MaintenanceRepository? = null

        fun getInstance(dao: MaintenanceDAO): MaintenanceRepository {
            return instance ?: synchronized(this) {
                instance ?: MaintenanceRepository(dao).also { instance = it }
            }
        }
    }
}