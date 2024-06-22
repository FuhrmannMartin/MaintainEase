package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import com.example.maintainease.data.entities.Maintenance
import com.example.maintainease.data.entities.getCurrentUser
import com.example.maintainease.repositories.MaintenanceRepository

class NewTaskScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {

    suspend fun addMaintenance(maintenance: Maintenance){
        getCurrentUser(1,2)["teamId"]?.let { maintenance.teamId = it }
        repository.addMaintenanceWithRelation(maintenance)
    }
}