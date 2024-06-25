package com.example.maintainease.repositories

import android.util.Log
import androidx.room.Transaction
import com.example.maintainease.data.dao.MaintenanceDAO
import com.example.maintainease.data.dao.StaffDAO
import com.example.maintainease.data.dao.TeamDAO
import com.example.maintainease.data.entities.Maintenance
import com.example.maintainease.data.entities.MaintenanceWithAssignee
import com.example.maintainease.data.entities.Staff
import com.example.maintainease.data.entities.Team
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MaintenanceRepository(private val maintenanceDAO: MaintenanceDAO, private val staffDAO: StaffDAO, private val teamDAO: TeamDAO) {
    private var currentUser: Map<String, Int> = mapOf("staffId" to 0, "teamId" to 0)

    fun setCurrentUser(user: Map<String, Int>) {
        currentUser = user
    }

    fun getCurrentUser(): Map<String, Int> {
        return currentUser
    }

    fun getFullStaff(): Flow<List<Staff>?> {
        return staffDAO.getFullStaff()
    }

    fun getAllTeams(): Flow<List<Team>?> {
        return teamDAO.getAllTeams()
    }

    fun getCurrentUserName(): Flow<String>? {
        return currentUser["staffId"]?.let { staffDAO.getStaffName(it) }
    }

    @Transaction
    suspend fun addMaintenanceWithRelation(maintenance: Maintenance) {
        val maintenanceId = maintenanceDAO.insertMaintenance(maintenance)
        maintenanceDAO.insertStaffMaintenanceRelation(maintenanceId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllMaintenanceWithAssignee(): Flow<List<MaintenanceWithAssignee>>? {
        Log.d("LoginUI", "repo staff: ${currentUser["staffId"]}")
        Log.d("LoginUI", "repo team: ${currentUser["teamId"]}")
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

    suspend fun updateStatus(maintenance: Maintenance){
        maintenanceDAO.updateMaintenance(maintenance)
    }
    suspend fun deleteMaintenance(maintenance: Maintenance){
        maintenanceDAO.deleteMaintenance(maintenance)
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