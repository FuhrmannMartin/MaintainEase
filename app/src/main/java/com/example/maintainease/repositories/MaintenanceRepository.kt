package com.example.maintainease.repositories

import androidx.room.Transaction
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.MaintenanceDAO
import com.example.maintainease.data.MaintenanceWithAssignee
import com.example.maintainease.data.Staff
import com.example.maintainease.data.StaffDAO
import com.example.maintainease.data.TeamDAO
import com.example.maintainease.data.getCurrentUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MaintenanceRepository(private val maintenanceDAO: MaintenanceDAO, private val staffDAO: StaffDAO, private val teamDAO: TeamDAO) {
    val currentUser = getCurrentUser()

    @Transaction
    suspend fun addMaintenanceWithRelation(maintenance: Maintenance) {
        val maintenanceId = maintenanceDAO.insertMaintenance(maintenance)
        maintenanceDAO.insertStaffMaintenanceRelation(maintenanceId)
    }

    fun getAllMaintenance(): Flow<List<Maintenance>>? {
        return currentUser["teamId"]?.let { maintenanceDAO.getAllMaintenance(it) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllMaintenanceWithAssignee(): Flow<List<MaintenanceWithAssignee>>? {
        return currentUser["teamId"]?.let { teamId ->
            maintenanceDAO.getAllMaintenance(teamId).flatMapLatest { maintenances ->
                val flows: List<Flow<MaintenanceWithAssignee>> = maintenances.map { maintenance ->
                    maintenanceDAO.getAssigneeIdForTask(maintenance.id)
                        .flatMapLatest { staffId ->
                            if (staffId != null) {
                                maintenanceDAO.getAssigneeById(staffId)
                            } else {
                                flowOf(null)
                            }
                        }.map { assignee ->
                            MaintenanceWithAssignee(maintenance, assignee)
                        }
                }
                combine(flows) { arrayOfMaintenancesWithAssignee ->
                    arrayOfMaintenancesWithAssignee.toList()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMaintenanceWithAssigneeById(taskId: Int): Flow<MaintenanceWithAssignee?> {
        return maintenanceDAO.getMaintenanceById(taskId).flatMapLatest { maintenance ->
            if (maintenance != null) {
                maintenanceDAO.getAssigneeIdForTask(taskId).flatMapLatest { staffId ->
                    if (staffId != null) {
                        maintenanceDAO.getAssigneeById(staffId).map { assignee ->
                            MaintenanceWithAssignee(maintenance, assignee)
                        }
                    } else {
                        flowOf(MaintenanceWithAssignee(maintenance, null))
                    }
                }
            } else {
                flowOf(null)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAssignee(taskId: Int): Flow<Staff?> {
        return maintenanceDAO.getAssigneeIdForTask(taskId).flatMapLatest { staffId ->
            if (staffId != null) {
                maintenanceDAO.getAssigneeById(staffId)
            } else {
                flowOf(null) // Return a Flow emitting null if staffId is null
            }
        }
    }

    suspend fun assignToMe(taskId: Int, staffId: Int) {
        maintenanceDAO.mapTaskToStaff(taskId = taskId, staffId = staffId)
    }

    suspend fun addCommentToTask(maintenance: Maintenance) {
        maintenanceDAO.updateMaintenance(maintenance)
    }

    companion object {
        @Volatile
        private var instance: MaintenanceRepository? = null

        fun getInstance(maintenanceDAO: MaintenanceDAO, staffDAO: StaffDAO, teamDAO: TeamDAO): MaintenanceRepository {
            return instance ?: synchronized(this) {
                instance ?: MaintenanceRepository(maintenanceDAO, staffDAO, teamDAO).also { instance = it }
            }
        }
    }
}