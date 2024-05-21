package com.example.maintainease.repositories

import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.MaintenanceDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MaintenanceRepository(private val maintenanceDAO: MaintenanceDAO) {
    fun getAllMaintenance(): Flow<List<Maintenance>> {
        return maintenanceDAO.getAllMaintenance()
    }

    fun getMaintenanceById(taskId: Int): Flow<Maintenance?> {
        return maintenanceDAO.getMaintenanceById(taskId)
    }

    suspend fun update(maintenances: List<Maintenance>) {
        withContext(Dispatchers.IO) {
            maintenanceDAO.updateMaintenance(maintenances)
        }
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