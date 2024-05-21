package com.example.maintainease.repositories

import androidx.room.Transaction
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.MaintenanceDAO
import com.example.maintainease.data.Staff
import com.example.maintainease.data.StaffDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class MaintenanceRepository(private val maintenanceDAO: MaintenanceDAO, private val staffDAO: StaffDAO) {

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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAssignee(taskId: Int): Flow<Staff?> {
        return maintenanceDAO.getAssigneeId(taskId).flatMapLatest { staffId ->
            if (staffId != null) {
                maintenanceDAO.getAssigneeById(staffId)
            } else {
                flowOf(null) // Return a Flow emitting null if staffId is null
            }
        }
    }

    suspend fun assignToMe(taskId: Int, name: String) {
        val staff = staffDAO.getStaffByName(name)
        if (staff != null) {
            maintenanceDAO.assignToMe(taskId = taskId, staffId = staff.id)
        }
    }

    companion object {
        @Volatile
        private var instance: MaintenanceRepository? = null

        fun getInstance(maintenanceDAO: MaintenanceDAO, staffDAO: StaffDAO): MaintenanceRepository {
            return instance ?: synchronized(this) {
                instance ?: MaintenanceRepository(maintenanceDAO, staffDAO).also { instance = it }
            }
        }
    }
}