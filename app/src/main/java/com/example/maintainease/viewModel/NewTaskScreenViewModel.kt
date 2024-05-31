package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.getCurrentUser
import com.example.maintainease.repositories.MaintenanceRepository

class NewTaskScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {

    suspend fun addMaintenance(maintenance: Maintenance){
        getCurrentUser()["teamId"]?.let { maintenance.teamId = it }
        repository.addNewMaintenanceTask(maintenance)
    }
}