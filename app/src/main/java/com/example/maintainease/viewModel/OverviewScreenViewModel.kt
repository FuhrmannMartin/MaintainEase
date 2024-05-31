package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maintainease.data.entities.MaintenanceWithAssignee
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OverviewScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {
    private val _maintenances = MutableStateFlow<List<MaintenanceWithAssignee>>(emptyList())
    val currentUser = repository.currentUser

    init {
        viewModelScope.launch {
            if (currentUser["staffId"] != null) {
                repository.getAllMaintenanceWithAssignee()?.collectLatest { maintenanceList ->
                    _maintenances.value = maintenanceList
                }
            }
        }
    }


    val openMaintenances: StateFlow<List<MaintenanceWithAssignee>> = _maintenances.map { maintenances ->
        maintenances.filter { it.maintenance.status == "open" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val inProgressMaintenances: StateFlow<List<MaintenanceWithAssignee>> = _maintenances.map { maintenances ->
        maintenances.filter { it.maintenance.status == "in progress" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val doneMaintenances: StateFlow<List<MaintenanceWithAssignee>> = _maintenances.map { maintenances ->
        maintenances.filter { it.maintenance.status == "done" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val cancelledMaintenances: StateFlow<List<MaintenanceWithAssignee>> = _maintenances.map { maintenances ->
        maintenances.filter { it.maintenance.status == "cancelled" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}